package ddd.persistence;


import ddd.AggregateRoot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AggregateRepository<R extends AggregateRoot, T extends TransactionContainer> extends Repository<T>
{
    private List<R> addedOrUpdatedAggregates = new ArrayList<>();

    public List<R> getAddedOrUpdatedAggregates()
    {
        return Collections.unmodifiableList(addedOrUpdatedAggregates);
    }

    protected AggregateRepository(T transactionContainer)
    {
        super(transactionContainer);
    }

    public void add(R aggregateRoot)
    {
        this.addedOrUpdatedAggregates.add(aggregateRoot);
    }

    public void update(R aggregateRoot)
    {
        this.addedOrUpdatedAggregates.add(aggregateRoot);
    }
}
