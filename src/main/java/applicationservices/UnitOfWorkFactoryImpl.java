package applicationservices;

import ddd.domainevents.DomainEventsRegistration;
import ddd.persistence.JpaTransactionContainer;
import ddd.persistence.UnitOfWork;
import ddd.persistence.UnitOfWorkFactory;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

public class UnitOfWorkFactoryImpl implements UnitOfWorkFactory
{
    @Inject
    private EntityManagerFactory entityManagerFactory;

    @Inject
    private DomainEventsRegistration domainEventsRegistration;

    public UnitOfWork create()
    {
        return new UnitOfWork(new JpaTransactionContainer(entityManagerFactory.createEntityManager()), domainEventsRegistration);
    }
}
