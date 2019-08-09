package frog.calculator.operator;

import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;

public class DelegateOperator implements IOperator {
    @Override
    public IExpression operate(String symbol, IExpressionContext context, IExpression[] expressions) {
        IExpression caller = expressions[0];
        IExpression executor = expressions[1];
        IExpression expression = caller.assembleTree(executor);
        context.addLocalVariable(expression);
        return expression;
    }
}
