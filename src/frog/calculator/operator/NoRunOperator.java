package frog.calculator.operator;

import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;

public class NoRunOperator implements IOperator {
    @Override
    public IExpression operate(String symbol, IExpressionContext context, IExpression[] expressions) {
        if(expressions.length > 1){
            throw new IllegalStateException("can't deal.");
        }
        return expressions.length == 1 ? expressions[0] : null;
    }

    private static NoRunOperator operator = new NoRunOperator();

    public static NoRunOperator getInstance(){
        return operator;
    }
}
