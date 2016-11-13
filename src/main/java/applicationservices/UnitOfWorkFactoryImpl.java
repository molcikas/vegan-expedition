package applicationservices;

import ddd.persistence.JpaTransactionContainer;
import ddd.persistence.UnitOfWork;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

public class UnitOfWorkFactoryImpl implements UnitOfWorkFactory
{
    @Inject
    private EntityManagerFactory entityManagerFactory;

    public UnitOfWork create()
    {
        return new UnitOfWork(new JpaTransactionContainer(entityManagerFactory.createEntityManager()));
    }
}
