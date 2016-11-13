package ddd.exceptions;

public class IllegalArgumentForDomainException extends IllegalArgumentException
{
    public IllegalArgumentForDomainException(String message)
    {
        super(message);
    }

    public IllegalArgumentForDomainException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
