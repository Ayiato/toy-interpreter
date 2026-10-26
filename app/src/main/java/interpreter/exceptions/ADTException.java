package interpreter.exceptions;

/**
 * Thrown when an operation on one of our ADTs cannot be done,
 * e.g. pop from an empty stack, lookup of a missing key, index out of bounds.
 */
public class ADTException extends MyException {
    public ADTException(String message) {
        super(message);
    }
}