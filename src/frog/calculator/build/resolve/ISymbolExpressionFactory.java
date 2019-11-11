package frog.calculator.build.resolve;

import frog.calculator.express.IExpression;

public interface ISymbolExpressionFactory {

    IExpression createExpression(String symbol);

}
