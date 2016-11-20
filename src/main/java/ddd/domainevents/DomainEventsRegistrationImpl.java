package ddd.domainevents;

import java.util.*;

public class DomainEventsRegistrationImpl implements DomainEventsRegistration
{
    private Map<Class, List<DomainEventHandler>> handlers = new HashMap<>();

    @Override
    public List<DomainEventHandler> getHandlersForDomainEvent(DomainEvent event)
    {
        List<DomainEventHandler> handlersForEvent = handlers.get(event.getClass());
        return handlersForEvent != null ? handlersForEvent : Collections.emptyList();
    }

    @Override
    public void registerEventHandler(DomainEventHandler eventHandler, Class eventClass)
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
