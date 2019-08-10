package frog.calculator.resolver.resolve.factory;

import frog.calculator.express.IExpression;
import frog.calculator.express.container.CustomFunctionExpression;

public class CustomFunctionExpressionFactory implements ISymbolExpressionFactory {

    private String closeSymbol;

    private String splitSymbol;

    /**
     * define a custom function expression factory
     * @param closeSymbol function signature part close symbol
     * @param splitSymbol function arguments split symbol
     */
    public CustomFunctionExpressionFactory(String closeSymbol, String splitSymbol) {
        this.closeSymbol = closeSymbol;
        this.splitSymbol = splitSymbol;
    }

    @Override
    public IExpression createExpression(String symbol) {
        return new CustomFunctionExpression(symbol, splitSymbol, closeSymbol);
    }

}
