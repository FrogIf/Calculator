package frog.calculator.dimpl.opr;

import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.operator.IOperator;

public abstract class AbstractOperator implements IOperator {

    protected abstract IExpression operate(String symbol, IExpression... expressions);

    @Override
    public IExpression operate(String symbol, IExpressionContext context, IExpression... expressions) {
        return this.operate(symbol, expressions);
    }
}
