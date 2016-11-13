package ddd.domainevents;

import ddd.persistence.UnitOfWork;

import java.util.*;

public class DomainEvents
{
    private static Map<Class, List<DomainEventHandler>> handlers = new HashMap<>();

    public static List<DomainEventHandler> getHandlersForDomainEvent(DomainEvent event)
    {
        List<DomainEventHandler> handlersForEvent = handlers.get(event.getClass());
        return handlersForEvent != null ? handlersForEvent : Collections.emptyList();
    }

    public static void registerEventHandler(DomainEventHandler eventHandler, Class eventClass)
    {
        List<DomainEventHandler> existingHandlers = handlers.get(eventClass);
        if(existingHandlers == null)
        {
            existingHandlers = new ArrayList<>();
            handlers.put(eventClass, existingHandlers);
        }
        existingHandlers.add(eventHandler);
    }
}
