package interpreter.model.exp;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyHeap;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LogicExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    private static IExp bool(boolean b) {
        return new ValueExp(new BoolValue(b));
    }

    @Test
    void and() throws MyException {
        assertEquals(new BoolValue(true), new LogicExp(LogicOp.AND, bool(true), bool(true)).eval(symTable, new MyHeap()));
        assertEquals(new BoolValue(false), new LogicExp(LogicOp.AND, bool(true), bool(false)).eval(symTable, new MyHeap()));
    }

    @Test
    void or() throws MyException {
        assertEquals(new BoolValue(true), new LogicExp(LogicOp.OR, bool(false), bool(true)).eval(symTable, new MyHeap()));
        assertEquals(new BoolValue(false), new LogicExp(LogicOp.OR, bool(false), bool(false)).eval(symTable, new MyHeap()));
    }

    @Test
    void nonBooleanOperandThrows() {
        IExp e = new LogicExp(LogicOp.AND, new ValueExp(new IntValue(1)), bool(true));
        var ex = assertThrows(ExpressionException.class, () -> e.eval(symTable, new MyHeap()));
        assertEquals("first operand is not a boolean", ex.getMessage());
    }
}