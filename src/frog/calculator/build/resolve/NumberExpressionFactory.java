package frog.calculator.build.resolve;

import frog.calculator.express.EndPointExpression;
import frog.calculator.express.IExpression;
import frog.calculator.execute.IOperator;
import frog.calculator.execute.base.NumberOpr;

public class NumberExpressionFactory implements ISymbolExpressionFactory {

    private final IOperator operator = new NumberOpr();

    @Override
    public IExpression createExpression(String symbol) {
        return new EndPointExpression(symbol, operator);
    }
}
