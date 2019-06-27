package frog.calculator.express.result;

import frog.calculator.express.IExpression;
import frog.calculator.operate.IOperator;

public abstract class ResultExpression implements IExpression {

    public abstract String resultValue();

    @Override
    public int priority() {
        return 100;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public boolean createBranch(IExpression expression) {
        return false;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        return null;
    }

    @Override
    public void setOperator(IOperator operator) {
    }
}
