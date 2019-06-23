package frog.calculator.expression.mid;

import frog.calculator.expression.IExpression;

public class SubExpression extends MidExpression {
    private static final String SYMBOL = "-";

    private IExpression left;

    private IExpression right;

    private static final int priority = 1;

    @Override
    public IExpression getLeft() {
        return left;
    }

    @Override
    public void setLeft(IExpression left) {
        this.left = left;
    }

    @Override
    public IExpression getRight() {
        return right;
    }

    @Override
    public void setRight(IExpression right) {
        this.right = right;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public double interpret() {
        return left.interpret() - right.interpret();
    }

    @Override
    public String toString() {
        return SYMBOL;
    }
}
