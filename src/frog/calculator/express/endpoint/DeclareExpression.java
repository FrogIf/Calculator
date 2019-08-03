package frog.calculator.express.endpoint;

import frog.calculator.express.IExpression;

public class DeclareExpression extends EndPointExpression {
    public DeclareExpression(String symbol) {
        super(symbol, null);
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        return expression.assembleTree(this);
    }
}
