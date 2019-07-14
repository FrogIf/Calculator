package frog.calculator.strimpl.opr.single;

import frog.calculator.express.IExpression;
import frog.calculator.operater.IOperator;
import frog.calculator.strimpl.StringResultExpression;

public class RightExpOperator implements IOperator {
    @Override
    public IExpression operate(String symbol, IExpression... expressions) {
        if(expressions.length != 1 || expressions[0] == null){
            throw new IllegalArgumentException("expression argument error.");
        }
        return new StringResultExpression(expressions[0].interpret().symbol() + symbol);
    }
}
