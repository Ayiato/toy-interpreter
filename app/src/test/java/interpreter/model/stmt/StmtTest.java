package interpreter.model.stmt;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() throws MyException {
        state = PrgState.of(new NopStmt());
        state.getExeStack().pop();
    }
    @Test
    void compStmtPushesSecondThenFirst() throws MyException {
        IStmt first = new NopStmt();
        IStmt second = new PrintStmt(new ValueExp(new IntValue(1)));
        new CompStmt(first, second).execute(state);
        assertEquals(first, state.getExeStack().pop());
        assertEquals(second, state.getExeStack().pop());
    }

    @Test
    void varDeclAddsDefaultValue() throws MyException {
        new VarDeclStmt("v", new IntType()).execute(state);
        new VarDeclStmt("b", new BoolType()).execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("v"));
        assertEquals(new BoolValue(false), state.getSymTable().lookup("b"));
    }

    @Test
    void varDeclTwiceThrows() throws MyException {
        new VarDeclStmt("v", new IntType()).execute(state);
        assertThrows(StatementException.class, () -> new VarDeclStmt("v", new IntType()).execute(state));
    }

    @Test
    void assignChangesTheValue() throws MyException {
        new VarDeclStmt("v", new IntType()).execute(state);
        new AssignStmt("v", new ValueExp(new IntValue(2))).execute(state);
        assertEquals(new IntValue(2), state.getSymTable().lookup("v"));
    }

    @Test
    void assignToUndeclaredVariableThrows() {
        assertThrows(StatementException.class,
                () -> new AssignStmt("x", new ValueExp(new IntValue(2))).execute(state));
    }

    @Test
    void assignWithWrongTypeThrows() throws MyException {
        new VarDeclStmt("v", new IntType()).execute(state);
        assertThrows(StatementException.class,
                () -> new AssignStmt("v", new ValueExp(new BoolValue(true))).execute(state));
    }

    @Test
    void printAddsToOut() throws MyException {
        new PrintStmt(new ValueExp(new IntValue(7))).execute(state);
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(7), state.getOut().get(0));
    }

    @Test
    void ifPushesThenBranchWhenTrue() throws MyException {
        IStmt thenS = new PrintStmt(new ValueExp(new IntValue(1)));
        IStmt elseS = new PrintStmt(new ValueExp(new IntValue(2)));
        new IfStmt(new ValueExp(new BoolValue(true)), thenS, elseS).execute(state);
        assertEquals(thenS, state.getExeStack().pop());
    }

    @Test
    void ifPushesElseBranchWhenFalse() throws MyException {
        IStmt thenS = new PrintStmt(new ValueExp(new IntValue(1)));
        IStmt elseS = new PrintStmt(new ValueExp(new IntValue(2)));
        new IfStmt(new ValueExp(new BoolValue(false)), thenS, elseS).execute(state);
        assertEquals(elseS, state.getExeStack().pop());
    }

    @Test
    void ifWithNonBooleanConditionThrows() {
        IStmt s = new IfStmt(new ValueExp(new IntValue(1)), new NopStmt(), new NopStmt());
        assertThrows(StatementException.class, () -> s.execute(state));
    }

    @Test
    void nopChangesNothing() throws MyException {
        new NopStmt().execute(state);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(0, state.getOut().size());
    }

    @Test
    void printOfUndefinedVariableThrows() {
        assertThrows(MyException.class, () -> new PrintStmt(new VarExp("x")).execute(state));
    }
}