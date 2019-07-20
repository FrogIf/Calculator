package frog.calculator.strimpl.opr.end;

import frog.calculator.express.IExpression;
import frog.calculator.operator.IOperator;
import frog.calculator.strimpl.StringResultExpression;

public class StringEndOperator implements IOperator {

    @Override
    public IExpression operate(String symbol, IExpression... expressions) {
        return new StringResultExpression(symbol);
    }
}
