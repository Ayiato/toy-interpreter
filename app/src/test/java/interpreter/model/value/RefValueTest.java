package interpreter.model.value;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import org.junit.jupiter.api.Test;

class RefValueTest {

    @Test
    void defaultValueIsAddressZero() {
        assertEquals(new RefValue(0, new IntType()), new RefType(new IntType()).defaultValue());
    }

    @Test
    void nestedRefTypesAreCompared() {
        assertEquals(new RefType(new RefType(new IntType())), new RefType(new RefType(new IntType())));
        assertNotEquals(new RefType(new RefType(new IntType())), new RefType(new IntType()));
    }
}