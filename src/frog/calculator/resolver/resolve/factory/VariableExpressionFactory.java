package frog.calculator.resolver.resolve.factory;

import frog.calculator.express.endpoint.VariableExpression;

public class VariableExpressionFactory implements ISymbolExpressionFactory {

    @Override
    public VariableExpression createExpression(String symbol) {
        return new VariableExpression(symbol);
    }
}
