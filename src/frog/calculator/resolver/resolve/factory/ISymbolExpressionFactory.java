package frog.calculator.resolver.resolve.factory;

import frog.calculator.express.IExpression;

public interface ISymbolExpressionFactory {

    IExpression createExpression(String symbol);

}
