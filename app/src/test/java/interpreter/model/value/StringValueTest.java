package interpreter.model.value;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.model.type.StringType;
import org.junit.jupiter.api.Test;

class StringValueTest {

    @Test
    void defaultValueIsEmptyString() {
        assertEquals(new StringValue(""), new StringType().defaultValue());
    }

    @Test
    void equalStringsHaveSameHashCode() {
        assertEquals(new StringValue("a"), new StringValue("a"));
        assertEquals(new StringValue("a").hashCode(), new StringValue("a").hashCode());
    }
}