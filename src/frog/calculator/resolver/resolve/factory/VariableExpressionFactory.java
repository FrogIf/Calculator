package frog.calculator.resolver.resolve.factory;

import frog.calculator.express.IExpression;
import frog.calculator.express.variable.VariableExpression;

public class VariableExpressionFactory implements ICustomSymbolExpressionFactory {

    private String assignSymbol;

    public VariableExpressionFactory(String assignSymbol) {
        this.assignSymbol = assignSymbol;
    }

    @Override
    public IExpression createExpression(String symbol) {
        return new VariableExpression(symbol, assignSymbol);
    }
}
