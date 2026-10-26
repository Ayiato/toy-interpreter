package interpreter.exceptions;

/**
 * Thrown when a statement cannot be executed,
 * e.g. empty execution stack, variable declared twice or not declared,
 * type mismatch in assignment, non-boolean condition.
 */
public class StatementException extends MyException {
    public StatementException(String message) {
        super(message);
    }
}