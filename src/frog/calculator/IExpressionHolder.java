package frog.calculator;

import frog.calculator.express.IExpression;

public interface IExpressionHolder {

    IExpression getSubExpression();

    IExpression getAddExpression();

    IExpression[] getInnerExpression();

    IExpression getSplitorExpression();

    IExpression getDeclareExpression();

}
