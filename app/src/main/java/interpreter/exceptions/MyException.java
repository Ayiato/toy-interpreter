package interpreter.exceptions;

/**
 * Base class of all interpreter errors.
 * It extends Exception, so it is a checked exception:
 * the compiler forces us to either catch it or declare it with "throws".
 */
public class MyException extends Exception {
    public MyException(String message) {
        super(message);
    }
}