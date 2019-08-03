package frog.calculator.resolver.resolve.factory;

import frog.calculator.express.endpoint.VariableExpression;

public class VariableExpressionFactory implements ICustomSymbolExpressionFactory {

    @Override
    public VariableExpression createExpression(String symbol) {
        return new VariableExpression(symbol);
    }
}
