package interpreter.model.adt;

import interpreter.exceptions.ADTException;

/**
 * A generic dictionary (map from keys to values).
 * Used for the SymTable: variable name -> current value.
 *
 * @param <K> the type of the keys
 * @param <V> the type of the values
 */
public interface IDictionary<K, V> {
    /** Returns true if the key exists in the dictionary. */
    boolean isDefined(K key);

    /** Returns the value of the key. Throws if the key does not exist. */
    V lookup(K key) throws ADTException;

    /** Adds a new key with its value (used when a variable is declared). */
    void put(K key, V value);

    /** Changes the value of an existing key. Throws if the key does not exist. */
    void update(K key, V value) throws ADTException;
}