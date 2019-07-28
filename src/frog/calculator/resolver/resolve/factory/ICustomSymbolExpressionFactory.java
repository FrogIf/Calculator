package frog.calculator.resolver.resolve.factory;

import frog.calculator.express.IExpression;

public interface ICustomSymbolExpressionFactory {

    IExpression createExpression(String symbol);

}
