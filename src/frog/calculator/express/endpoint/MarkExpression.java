package frog.calculator.express.endpoint;

import frog.calculator.express.IExpression;

public class MarkExpression extends EndPointExpression {

    public MarkExpression(String symbol) {
        super(symbol, null);
    }

    @Override
    public IExpression interpret() {
        throw new IllegalStateException("access illegal.");
    }

}
