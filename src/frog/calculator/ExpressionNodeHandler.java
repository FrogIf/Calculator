package frog.calculator;

import frog.calculator.express.IExpression;

public interface ExpressionNodeHandler {

    IExpression handleExpression(IExpression expression);

}
