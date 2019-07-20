package frog.calculator.operator;

import frog.calculator.express.IExpression;

public class StructContainerOperator implements IOperator {

    @Override
    public IExpression operate(String symbol, IExpression... expressions) {
        if(expressions.length > 2 || expressions.length < 1){
            throw new IllegalArgumentException("can't operate.");
        }

        if(expressions[0] == null){
            throw new IllegalArgumentException("empty container.");
        }

        return expressions[0].interpret();
    }

}
