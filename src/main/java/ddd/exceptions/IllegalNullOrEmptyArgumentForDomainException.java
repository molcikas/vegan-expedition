package ddd.exceptions;

public class IllegalNullOrEmptyArgumentForDomainException extends IllegalArgumentException
{
    public IllegalNullOrEmptyArgumentForDomainException(String argument)
    {
        super(String.format("%s cannot be null or empty.", argument));
    }
}
