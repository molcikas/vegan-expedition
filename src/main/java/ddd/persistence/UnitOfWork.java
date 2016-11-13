package ddd.persistence;

import ddd.AggregateRoot;
import ddd.domainevents.DomainEvent;
import ddd.domainevents.DomainEventHandler;
import ddd.domainevents.DomainEvents;

import java.io.Closeable;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UnitOfWork implements Closeable
{
    private TransactionContainer transactionContainer;
    private List<Repository> repositories;
    private boolean isCommitted = false;

    public UnitOfWork(TransactionContainer transactionContainer)
    {
        this(transactionContainer, Collections.emptyList());
    }

    public <T extends Repository> UnitOfWork(TransactionContainer transactionContainer, Class<T> repositoryClass)
    {
        this(transactionContainer, Collections.singletonList(repositoryClass));
    }

    public <T extends Repository> UnitOfWork(TransactionContainer transactionContainer, List<Class<T>> repositoryClasses)
    {
        this.transactionContainer = transactionContainer;
        this.repositories = repositoryClasses.stream().map(this::createRepository).collect(Collectors.toList());
    }

    public <T extends Repository> T getRepository(Class<T> repositoryClass)
    {
        for(Repository repository : repositories)
        {
            if(repository.getClass().equals(repositoryClass))
            {
                return (T) repository;
            }
        }

        // If a repository of the requested type does it exist, create one dynamically.
        Repository newRepository = createRepository(repositoryClass);
        this.repositories.add(newRepository);

        return (T) newRepository;
    }

    public void commit()
    {
        if(isCommitted)
        {
            throw new IllegalStateException("A unit of work cannot be committed more than once. Create a new unit of work instead.");
        }

        // We need to retrieve all the domain events that need to be dispatched, then dispatch them. The domain
        // events are inside AggregateRoots, which are inside AggregateRepositories.

        List<AggregateRepository> aggregateRepositories = repositories
            .stream()
            .filter(r -> AggregateRepository.class.isAssignableFrom(r.getClass()))
            .map(r -> (AggregateRepository) r)
            .collect(Collectors.toList());

        List<AggregateRoot> aggregatesWithDomainEvents = aggregateRepositories
            .stream()
            .map(ar -> (List<AggregateRoot>) ar.getAddedOrUpdatedAggregates())
            .flatMap(Collection::stream)
            .filter(a -> a.getGeneratedDomainEvents().size() > 0)
            .collect(Collectors.toList());

        List<DomainEvent> domainEvents = aggregatesWithDomainEvents
            .stream()
            .map(AggregateRoot::getGeneratedDomainEvents)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        Map<DomainEvent, List<DomainEventHandler>> domainEventsAndHandlers = domainEvents
            .stream()
            .collect(Collectors.toMap(Function.identity(), DomainEvents::getHandlersForDomainEvent));

        dispatchEventsBeforeCommit(domainEventsAndHandlers);

        transactionContainer.commit();
        isCommitted = true;

        dispatchEventsAfterCommitAsync(domainEventsAndHandlers);
        aggregatesWithDomainEvents.forEach(AggregateRoot::clearGeneratedDomainEvents);
    }

    @Override
    public void close()
    {
        try
        {
            transactionContainer.close();
        }
        catch(Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    private <T extends Repository> T createRepository(Class<T> repositoryClass)
    {
        Repository repository;
        Constructor constructor = Arrays.stream(repositoryClass.getDeclaredConstructors())
            .filter(c -> c.getParameterTypes().length == 1 && c.getParameterTypes()[0].equals(transactionContainer.getClass()))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(
                String.format("No constructor found for repository '%s' that has a constructor with parameter '%s'.",
                    repositoryClass.getSimpleName(),
                    transactionContainer.getClass().getSimpleName())
            ));
        try
        {
            repository = (Repository) constructor.newInstance(transactionContainer);
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
        return (T) repository;
    }

    private void dispatchEventsBeforeCommit(Map<DomainEvent, List<DomainEventHandler>> domainEventsAndHandlers)
    {
        for(DomainEvent domainEvent : domainEventsAndHandlers.keySet())
        {
            for(DomainEventHandler handlerForEvent : domainEventsAndHandlers.get(domainEvent))
            {
                handlerForEvent.handleEventJustBeforeCommit(domainEvent, this);
            }
        }
    }

    private void dispatchEventsAfterCommitAsync(Map<DomainEvent, List<DomainEventHandler>> domainEventsAndHandlers)
    {
        if(domainEventsAndHandlers.isEmpty())
        {
            return;
        }

        new Thread(() -> {
            DispatchAfterCommitException dispatchAfterCommitException = new DispatchAfterCommitException("One or more exceptions occurred while dispatching events after a commit.");
            for(DomainEvent domainEvent : domainEventsAndHandlers.keySet())
            {
                for(DomainEventHandler handlerForEvent : domainEventsAndHandlers.get(domainEvent))
                {
                    try
                    {
                        handlerForEvent.handleEventAfterCommit(domainEvent);
                    }
                    catch(Throwable ex)
                    {
                        dispatchAfterCommitException.addSuppressed(ex);
                    }
                }
            }
            if(dispatchAfterCommitException.getSuppressed().length > 0)
            {
                throw dispatchAfterCommitException;
            }
        }).start();
    }
}
