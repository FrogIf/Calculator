package frog.calculator;

import frog.calculator.expression.IExpression;

public interface ExpressionNodeHandler {

    IExpression handleExpression(IExpression expression);

}
