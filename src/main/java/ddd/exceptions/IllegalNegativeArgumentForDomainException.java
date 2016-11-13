package ddd.exceptions;

public class IllegalNegativeArgumentForDomainException extends IllegalArgumentException
{
    public IllegalNegativeArgumentForDomainException(String argument)
    {
        super(String.format("%s cannot be less than zero.", argument));
    }
}
