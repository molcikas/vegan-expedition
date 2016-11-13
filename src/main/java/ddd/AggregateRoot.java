package ddd;

import ddd.domainevents.DomainEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AggregateRoot
{
    private List<DomainEvent> generatedDomainEvents = new ArrayList<>();

    public List<DomainEvent> getGeneratedDomainEvents()
    {
        return Collections.unmodifiableList(generatedDomainEvents);
    }

    public void clearGeneratedDomainEvents()
    {
        generatedDomainEvents.clear();
    }

    protected void generateDomainEvent(DomainEvent event)
    {
        this.generatedDomainEvents.add(event);
    }
}
