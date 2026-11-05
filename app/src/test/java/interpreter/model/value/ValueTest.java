package interpreter.model.value;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.type.StringType;
import org.junit.jupiter.api.Test;

class ValueTest {

    @Test
    void intValueHasIntType() {
        assertEquals(new IntType(), new IntValue(5).getType());
    }

    @Test
    void boolValueHasBoolType() {
        assertEquals(new BoolType(), new BoolValue(true).getType());
    }

    @Test
    void sameValuesAreEqual() {
        assertEquals(new IntValue(2), new IntValue(2));
        assertEquals(new BoolValue(false), new BoolValue(false));
    }

    @Test
    void differentValuesAreNotEqual() {
        assertNotEquals(new IntValue(2), new IntValue(3));
        assertNotEquals(new IntValue(0), new BoolValue(false));
    }

    @Test
    void valuesPrintNicely() {
        assertEquals("2", new IntValue(2).toString());
        assertEquals("true", new BoolValue(true).toString());
    }
}

