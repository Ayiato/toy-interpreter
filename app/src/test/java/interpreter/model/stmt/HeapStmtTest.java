package interpreter.model.stmt;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ReadHeapExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HeapStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() throws MyException {
        state = PrgState.of(new NopStmt());
        state.getExeStack().pop();
        new VarDeclStmt("v", new RefType(new IntType())).execute(state);
        new VarDeclStmt("x", new IntType()).execute(state);
    }

    private static ValueExp num(int n) {
        return new ValueExp(new IntValue(n));
    }

    @Test
    void refVariableGetsAddressZero() throws MyException {
        assertEquals(new RefValue(0, new IntType()), state.getSymTable().lookup("v"));
    }

    @Test
    void newAllocatesAtAddressOneAndUpdatesTheVariable() throws MyException {
        new NewStmt("v", num(20)).execute(state);
        assertEquals(new RefValue(1, new IntType()), state.getSymTable().lookup("v"));
        assertEquals(new IntValue(20), state.getHeap().get(1));
    }

    @Test
    void newErrorsThrow() {
        assertThrows(StatementException.class, () -> new NewStmt("y", num(1)).execute(state));
        assertThrows(StatementException.class, () -> new NewStmt("x", num(1)).execute(state));
        assertThrows(StatementException.class,
                () -> new NewStmt("v", new ValueExp(new BoolValue(true))).execute(state));
    }

    @Test
    void readHeapReadsTheValue() throws MyException {
        new NewStmt("v", num(20)).execute(state);
        var value = new ReadHeapExp(new VarExp("v")).eval(state.getSymTable(), state.getHeap());
        assertEquals(new IntValue(20), value);
    }

    @Test
    void readHeapErrorsThrow() {
        assertThrows(ExpressionException.class,
                () -> new ReadHeapExp(num(1)).eval(state.getSymTable(), state.getHeap()));
        assertThrows(ExpressionException.class,
                () -> new ReadHeapExp(new VarExp("v")).eval(state.getSymTable(), state.getHeap()));
    }

    @Test
    void writeHeapChangesTheLocation() throws MyException {
        new NewStmt("v", num(20)).execute(state);
        new WriteHeapStmt("v", num(30)).execute(state);
        assertEquals(new IntValue(30), state.getHeap().get(1));
    }

    @Test
    void writeHeapErrorsThrow() throws MyException {
        assertThrows(StatementException.class, () -> new WriteHeapStmt("x", num(1)).execute(state));
        assertThrows(StatementException.class, () -> new WriteHeapStmt("v", num(1)).execute(state));
        new NewStmt("v", num(20)).execute(state);
        assertThrows(StatementException.class,
                () -> new WriteHeapStmt("v", new ValueExp(new BoolValue(true))).execute(state));
    }
}