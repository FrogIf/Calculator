package frog.calculator.expressext.container;

import frog.calculator.expressext.IExpression;

public class FunctionExpression extends ContainerExpression {

    private IExpression funBody;

    public FunctionExpression(String symbol, IExpression funBody) {
        super(symbol);
        this.funBody = funBody;
    }
}
