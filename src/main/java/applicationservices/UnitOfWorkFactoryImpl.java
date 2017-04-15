package applicationservices;

import ddd.domainevents.DomainEventsRegistration;
import ddd.persistence.PhotonTransactionContainer;
import ddd.persistence.UnitOfWork;
import ddd.persistence.UnitOfWorkFactory;
import photon.Photon;

import javax.inject.Inject;

public class UnitOfWorkFactoryImpl implements UnitOfWorkFactory
{
    @Inject
    private Photon photon;

    @Inject
    private DomainEventsRegistration domainEventsRegistration;

    public UnitOfWork create()
    {
        return new UnitOfWork(new PhotonTransactionContainer(photon), domainEventsRegistration);
    }
}
