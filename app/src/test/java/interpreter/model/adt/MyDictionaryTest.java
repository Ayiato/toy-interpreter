package interpreter.model.adt;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.exceptions.ADTException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyDictionaryTest {
    private IDictionary<String, Integer> dict;

    @BeforeEach
    void setUp() {
        dict = new MyDictionary<>();
    }

    @Test
    void putThenLookupReturnsTheValue() throws ADTException {
        dict.put("v", 2);
        assertTrue(dict.isDefined("v"));
        assertEquals(2, dict.lookup("v"));
    }

    @Test
    void updateChangesTheValue() throws ADTException {
        dict.put("v", 2);
        dict.update("v", 5);
        assertEquals(5, dict.lookup("v"));
    }

    @Test
    void missingKeyIsNotDefined() {
        assertFalse(dict.isDefined("x"));
    }

    @Test
    void lookupOfMissingKeyThrows() {
        assertThrows(ADTException.class, () -> dict.lookup("x"));
    }

    @Test
    void updateOfMissingKeyThrows() {
        assertThrows(ADTException.class, () -> dict.update("x", 1));
    }

    @Test
    void removeDeletesTheKey() throws ADTException {
        dict.put("v", 2);
        dict.remove("v");
        assertFalse(dict.isDefined("v"));
    }

    @Test
    void removeMissingKeyThrows() {
        assertThrows(ADTException.class, () -> dict.remove("x"));
    }

    @Test
    void toMapReturnsAllPairs() {
        dict.put("a", 1);
        dict.put("b", 2);
        assertEquals(Map.of("a", 1, "b", 2), dict.toMap());
    }
}