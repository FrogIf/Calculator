package frog.calculator.explain.resolve;

import frog.calculator.express.IExpression;

public interface ISymbolExpressionFactory {

    IExpression createExpression(String symbol);

}
