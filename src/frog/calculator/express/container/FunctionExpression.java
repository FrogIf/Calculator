package frog.calculator.express.container;

import frog.calculator.express.IExpression;
import frog.calculator.operater.IOperator;

public class FunctionExpression extends ContainerExpression {

    private IExpression funBody;

    public FunctionExpression(String symbol, IOperator operator, String closeSymbol, IExpression funBody) {
        super(symbol, operator, closeSymbol);
        this.funBody = funBody;
    }


}
