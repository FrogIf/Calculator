package frog.calculator.operator;

import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.express.container.CustomFunctionExpression;

public class DelegateOperator implements IOperator {
    @Override
    public IExpression operate(String symbol, IExpressionContext context, IExpression[] expressions) {
        IExpression func = expressions[0];
        IExpression body = expressions[1];

        if(func instanceof CustomFunctionExpression){
            CustomFunctionExpression cf = (CustomFunctionExpression) func;
            cf.delegate(body);
        }else{
            throw new IllegalArgumentException("can't support expression.");
        }

        return func;
    }
}
