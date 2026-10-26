package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Implementation of IStack that wraps a java.util.ArrayDeque.
 * We only "forward" the calls to the deque, and add our own error
 * (ADTException) when popping from an empty stack.
 */
public class MyStack<T> implements IStack<T> {
    private final Deque<T> stack = new ArrayDeque<>();

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public T pop() throws ADTException {
        if (stack.isEmpty()) {
            throw new ADTException("pop from an empty stack");
        }
        return stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        // ArrayDeque prints the top element first, e.g. [s1, s2, s3]
        return stack.toString();
    }
}