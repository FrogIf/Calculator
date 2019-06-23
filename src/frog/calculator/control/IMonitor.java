package frog.calculator.control;

import frog.calculator.expression.IExpression;

public interface IMonitor {

    void handle(IExpression expression);

}
