package ddd.domainevents;

import ddd.persistence.UnitOfWork;

import java.util.*;

public interface DomainEventsRegistration
{
    List<DomainEventHandler> getHandlersForDomainEvent(DomainEvent event);

    void registerEventHandler(DomainEventHandler eventHandler, Class eventClass);
}
