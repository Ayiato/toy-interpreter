package interpreter.model.adt;

import interpreter.exceptions.ADTException;

/**
 * A generic stack (last in, first out).
 * Used for the ExeStack: the statements that still have to be executed.
 *
 * @param <T> the type of the elements
 */
public interface IStack<T> {
    /** Puts an element on top of the stack. */
    void push(T v);

    /** Removes and returns the top element. Throws if the stack is empty. */
    T pop() throws ADTException;

    /** Returns true if there are no elements. */
    boolean isEmpty();
}