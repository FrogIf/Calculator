package frog.calculator.resolver.resolve.factory;

import frog.calculator.express.IExpression;
import frog.calculator.express.container.CustomFunctionExpression;

public class CustomFunctionExpressionFactory implements ICustomSymbolExpressionFactory {

    private String closeSymbol;

    private String splitSymbol;

    private String delegateSymbol;

    public CustomFunctionExpressionFactory(String closeSymbol, String splitSymbol, String delegateSymbol) {
        this.closeSymbol = closeSymbol;
        this.splitSymbol = splitSymbol;
        this.delegateSymbol = delegateSymbol;
    }

    @Override
    public IExpression createExpression(String symbol) {
        // 自定义函数
        return new CustomFunctionExpression(symbol, closeSymbol, splitSymbol, delegateSymbol);
    }

}
