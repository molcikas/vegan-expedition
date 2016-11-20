package ddd.persistence;

import ddd.persistence.UnitOfWork;

public interface UnitOfWorkFactory
{
    UnitOfWork create();
}
