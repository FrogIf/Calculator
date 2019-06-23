package frog.calculator.expression;

public class ResultExpression implements IExpression {
    @Override
    public double interpret() {
        return 0;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean isLeaf() {
        return false;
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
    public IExpression[] branches() {
        return new IExpression[0];
    }
}
