package interpreter.exceptions;

/**
 * Thrown when an expression cannot be evaluated,
 * e.g. division by zero, operand of the wrong type, undefined variable.
 */
public class ExpressionException extends MyException {
    public ExpressionException(String message) {
        super(message);
    }
}