package frog.calculator.operator;

import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;

public class DeclareOperator implements IOperator {
    @Override
    public IExpression operate(String symbol, IExpressionContext context, IExpression... expressions) {
        return expressions[0].interpret();
    }
}
