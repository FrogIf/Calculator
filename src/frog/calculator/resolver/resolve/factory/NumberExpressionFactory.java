package frog.calculator.resolver.resolve.factory;

import frog.calculator.express.IExpression;
import frog.calculator.express.endpoint.EndPointExpression;
import frog.calculator.operator.IOperator;

public class NumberExpressionFactory implements ISymbolExpressionFactory {

    private final IOperator operator;

    public NumberExpressionFactory(IOperator operator) {
        this.operator = operator;
    }

    @Override
    public IExpression createExpression(String symbol) {
        return new EndPointExpression(symbol, operator);
    }
}
