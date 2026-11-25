package interpreter.model.stmt;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.RelOp;
import interpreter.model.exp.RelationalExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

class WhileStmtTest {

    private int countLoops(int start) throws MyException {
        IStmt loop = new WhileStmt(
                new RelationalExp(RelOp.GT, new VarExp("v"), new ValueExp(new IntValue(0))),
                new CompStmt(new AssignStmt("x", new ArithExp(ArithOp.ADD, new VarExp("x"), new ValueExp(new IntValue(1)))),
                        new AssignStmt("v", new ArithExp(ArithOp.SUB, new VarExp("v"), new ValueExp(new IntValue(1))))));
        PrgState state = PrgState.of(new NopStmt());
        state.getExeStack().pop();
        new VarDeclStmt("v", new IntType()).execute(state);
        new VarDeclStmt("x", new IntType()).execute(state);
        new AssignStmt("v", new ValueExp(new IntValue(start))).execute(state);
        state.getExeStack().push(loop);
        while (!state.getExeStack().isEmpty()) {
            state.getExeStack().pop().execute(state);
        }
        return ((IntValue) state.getSymTable().lookup("x")).val();
    }

    @Test
    void loopRunsZeroOneAndSeveralTimes() throws MyException {
        assertEquals(0, countLoops(0));
        assertEquals(1, countLoops(1));
        assertEquals(5, countLoops(5));
    }

    @Test
    void whileWithANonBooleanConditionThrows() {
        var loop = new WhileStmt(new ValueExp(new IntValue(1)), new NopStmt());
        assertThrows(StatementException.class, () -> loop.execute(PrgState.of(new NopStmt())));
    }
}