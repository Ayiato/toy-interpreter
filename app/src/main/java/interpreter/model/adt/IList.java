package interpreter.model.adt;

import interpreter.exceptions.ADTException;

/**
 * A generic list (elements kept in the order they were added).
 * Used for Out: the values printed by the program.
 *
 * @param <T> the type of the elements
 */
public interface IList<T> {
    /** Adds an element at the end of the list. */
    void add(T v);

    /** Returns the element at the given position (0 = first). Throws if the index is invalid. */
    T get(int index) throws ADTException;

    /** Returns how many elements are in the list. */
    int size();
}