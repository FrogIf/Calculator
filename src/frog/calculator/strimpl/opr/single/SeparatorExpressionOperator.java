package frog.calculator.strimpl.opr.single;

import frog.calculator.express.IExpression;
import frog.calculator.operator.IOperator;
import frog.calculator.strimpl.StringResultExpression;

public class SeparatorExpressionOperator implements IOperator {
    @Override
    public IExpression operate(String symbol, IExpression... expressions) {
        if(expressions.length != 2 || expressions[0] == null || expressions[1] == null){
            throw new IllegalArgumentException("expression error.");
        }
        return new StringResultExpression(expressions[0].symbol() + symbol + expressions[1].symbol());
    }
}
