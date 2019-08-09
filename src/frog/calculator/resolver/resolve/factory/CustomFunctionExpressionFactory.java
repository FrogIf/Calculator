package frog.calculator.resolver.resolve.factory;

import frog.calculator.express.IExpression;
import frog.calculator.express.container.CustomFunctionExpression;

public class CustomFunctionExpressionFactory implements ISymbolExpressionFactory {

    private String closeSymbol;

    private String splitSymbol;

    private String delegateSymbol;

    /**
     * define a custom function expression factory
     * @param closeSymbol function signature part close symbol
     * @param splitSymbol function arguments split symbol
     * @param delegateSymbol function body define start symbol
     */
    public CustomFunctionExpressionFactory(String closeSymbol, String splitSymbol, String delegateSymbol) {
        this.closeSymbol = closeSymbol;
        this.splitSymbol = splitSymbol;
        this.delegateSymbol = delegateSymbol;
    }

    @Override
    public IExpression createExpression(String symbol) {
        return new CustomFunctionExpression(symbol, splitSymbol, closeSymbol, delegateSymbol);
    }

}
