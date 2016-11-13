package ddd.persistence;

public abstract class Repository<T extends TransactionContainer>
{
    /**
     * This will be called by the unit of work when a repository is attached to it. Override this in your repository
     * implementation class and save (as a private variable) the item(s) in the transaction container that are needed
     * to perform work. For example, if using a JpaTransactionContainer, save the EntityManager from the
     * JpaTransactionContainer.
     *
     * @param transactionContainer - The transaction container for the current transaction (and unit of work).
     */
    protected Repository(T transactionContainer)
    {
    }
}
