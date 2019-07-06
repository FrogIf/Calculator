package frog.calculator.express.result;

import frog.calculator.express.IExpression;

public abstract class AResultExpression implements IExpression {

    public abstract String resultValue();

    @Override
    public boolean leaf() {
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
    public IExpression clone() {
        try {
            return (IExpression) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
