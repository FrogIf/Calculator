package frog.calculator.resolver.resolve.factory;

import frog.calculator.express.EndPointExpression;
import frog.calculator.express.IExpression;
import frog.calculator.operator.IOperator;
import frog.calculator.operator.base.NumberOpr;

public class NumberExpressionFactory implements ISymbolExpressionFactory {

    private final IOperator operator = new NumberOpr();

    @Override
    public IExpression createExpression(String symbol) {
        return new EndPointExpression(symbol, operator);
    }
}
