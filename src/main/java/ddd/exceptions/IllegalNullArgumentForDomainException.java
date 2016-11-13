package ddd.exceptions;

public class IllegalNullArgumentForDomainException extends IllegalArgumentException
{
    public IllegalNullArgumentForDomainException(String argument)
    {
        super(String.format("%s cannot be null.", argument));
    }
}
