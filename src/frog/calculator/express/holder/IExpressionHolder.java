package frog.calculator.express.holder;

import frog.calculator.express.IExpression;

public interface IExpressionHolder {

    IExpression[] getExpressions();

    IExpression getExpressionBySymbol(String symbol);

}
