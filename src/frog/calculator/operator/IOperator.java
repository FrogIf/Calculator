package frog.calculator.operator;

import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;

public interface IOperator {

    IExpression operate(String symbol, IExpressionContext context, IExpression... expressions);

}
