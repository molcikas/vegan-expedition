package applicationservices;

import ddd.persistence.UnitOfWork;

public interface UnitOfWorkFactory
{
    UnitOfWork create();
}
