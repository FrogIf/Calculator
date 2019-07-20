package frog.calculator.express;

import frog.calculator.operator.IOperator;

public abstract class AbstractExpression implements IExpression{

    protected String symbol;

    protected int order = -1;

    protected IOperator operator;

    public AbstractExpression(String symbol, IOperator operator) {
        this.symbol = symbol;
        this.operator = operator;
    }

    @Override
    public String symbol() {
        return this.symbol;
    }

    @Override
    public IExpression clone() {
        try {
            return (IExpression) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public IOperator getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return this.symbol;
    }

    @Override
    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int order() {
        return this.order;
    }
}
