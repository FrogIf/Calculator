package frog.calculator.operator;

import frog.calculator.express.IExpression;

public class NoRunOperator implements IOperator {
    @Override
    public IExpression operate(String symbol, IExpression... expressions) {
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
