package ddd.exceptions;

public class IllegalStateForDomainException extends IllegalStateException
{
    public IllegalStateForDomainException(String message)
    {
        super(message);
    }
}
