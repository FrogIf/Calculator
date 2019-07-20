package frog.calculator.operator;

import frog.calculator.express.IExpression;

public interface IOperator {

    IExpression operate(String symbol, IExpression... expressions);

}
