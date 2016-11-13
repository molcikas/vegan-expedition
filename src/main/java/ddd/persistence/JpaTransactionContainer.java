package ddd.persistence;

import javax.persistence.EntityManager;

public class JpaTransactionContainer implements TransactionContainer
{
    private final EntityManager entityManager;

    /**
     * Call this in the repository constructor get retrieve the EntityManager, then use it to perform queries. DO
     * NOT CALL COMMIT OR FLUSH FROM THE REPOSITORY! The UnitOfWork will commit the transaction.
     *
     * @return
     */
    public EntityManager getEntityManager()
    {
        return entityManager;
    }

    public JpaTransactionContainer(EntityManager entityManager)
    {
        if(entityManager == null)
        {
            throw new IllegalArgumentException("entityManager cannot be null.");
        }
        if(!entityManager.isOpen())
        {
            throw new IllegalStateException("Cannot start a transaction with the provided EntityManager because it is closed.");
        }
        this.entityManager = entityManager;
        entityManager.getTransaction().begin();
    }

    @Override
    public void commit()
    {
        entityManager.getTransaction().commit();
    }

    @Override
    public void close()
    {
        if(entityManager.getTransaction().isActive())
        {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
    }
}
