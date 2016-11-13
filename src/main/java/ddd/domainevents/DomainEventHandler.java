package ddd.domainevents;

import ddd.persistence.UnitOfWork;

public interface DomainEventHandler<T extends DomainEvent>
{
    /**
     * Handle the event before commit so that the changes are included in the same transaction as whatever
     * caused this event to be generated.
     *
     * @param event
     * @param unitOfWork - The unit of work containing the transaction that caused this event to be generated.
     */
    default void handleEventJustBeforeCommit(T event, UnitOfWork unitOfWork)
    {
    }

    /**
     * Handle the event asynchronously after the commit has successfully occurred.
     *
     * @param event
     */
    default void handleEventAfterCommit(T event)
    {
    }

    /**
     * Returns the class of the DomainEvent that this handler handles.
     *
     * @return
     */
    Class<? extends DomainEvent> getEventClass();
}
