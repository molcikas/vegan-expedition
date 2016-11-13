package ddd.persistence;

public class DispatchAfterCommitException extends RuntimeException
{
    public DispatchAfterCommitException(String message)
    {
        super(message);
    }
}
