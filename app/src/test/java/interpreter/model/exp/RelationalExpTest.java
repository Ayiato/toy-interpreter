package interpreter.model.exp;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

class RelationalExpTest {

    private IValue eval(RelOp op, IValue a, IValue b) throws MyException {
        return new RelationalExp(op, new ValueExp(a), new ValueExp(b)).eval(new MyDictionary<>());
    }

    @Test
    void trueResults() throws MyException {
        IValue two = new IntValue(2), three = new IntValue(3);
        assertEquals(new BoolValue(true), eval(RelOp.LT, two, three));
        assertEquals(new BoolValue(true), eval(RelOp.LE, two, two));
        assertEquals(new BoolValue(true), eval(RelOp.EQ, two, two));
        assertEquals(new BoolValue(true), eval(RelOp.NE, two, three));
        assertEquals(new BoolValue(true), eval(RelOp.GT, three, two));
        assertEquals(new BoolValue(true), eval(RelOp.GE, three, three));
    }

    @Test
    void falseResults() throws MyException {
        IValue two = new IntValue(2), three = new IntValue(3);
        assertEquals(new BoolValue(false), eval(RelOp.LT, three, two));
        assertEquals(new BoolValue(false), eval(RelOp.LE, three, two));
        assertEquals(new BoolValue(false), eval(RelOp.EQ, two, three));
        assertEquals(new BoolValue(false), eval(RelOp.NE, two, two));
        assertEquals(new BoolValue(false), eval(RelOp.GT, two, three));
        assertEquals(new BoolValue(false), eval(RelOp.GE, two, three));
    }

    @Test
    void nonIntegerOperandThrows() {
        assertThrows(ExpressionException.class, () -> eval(RelOp.LT, new IntValue(1), new BoolValue(true)));
    }
}