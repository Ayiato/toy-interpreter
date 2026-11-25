package interpreter.model.adt;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyHeapTest {
    private IHeap heap;

    @BeforeEach
    void setUp() {
        heap = new MyHeap();
    }

    @Test
    void allocateGivesAddressesOneTwoThree() throws ADTException {
        assertEquals(1, heap.allocate(new IntValue(10)));
        assertEquals(2, heap.allocate(new IntValue(20)));
        assertEquals(3, heap.allocate(new IntValue(30)));
        assertEquals(new IntValue(20), heap.get(2));
    }

    @Test
    void updateChangesTheValue() throws ADTException {
        heap.allocate(new IntValue(10));
        heap.update(1, new IntValue(99));
        assertEquals(new IntValue(99), heap.get(1));
    }

    @Test
    void missingAddressThrows() {
        assertThrows(ADTException.class, () -> heap.get(5));
        assertThrows(ADTException.class, () -> heap.update(5, new IntValue(1)));
    }
}