package ddd.persistence;

public interface TransactionContainer extends AutoCloseable
{
    void commit();
}