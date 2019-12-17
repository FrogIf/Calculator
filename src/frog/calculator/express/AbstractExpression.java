package frog.calculator.express;

import frog.calculator.build.IExpressionBuilder;
import frog.calculator.exception.CalculatorError;
import frog.calculator.exec.IOperator;
import frog.calculator.exec.space.ISpace;
import frog.calculator.math.BaseNumber;

public abstract class AbstractExpression implements IExpression {

    protected String symbol;

    protected int order = -1;

    protected IOperator operator;

    protected IExpressionContext context;

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
            AbstractExpression expression = (AbstractExpression) super.clone();
            expression.context = null;
            return expression;
        } catch (CloneNotSupportedException e) {
            throw new CalculatorError("clone is failed.");
        }
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
    public int order() {
        return this.order;
    }

    @Override
    public IExpressionContext getContext() {
        return this.context;
    }

    @Override
    public ISpace<BaseNumber> interpret() {
        return this.operator.operate(this);
    }

    @Override
    public void buildInit(int order, IExpressionContext context, IExpressionBuilder builder) {
        this.order = order;
        this.context = context;
    }


}
