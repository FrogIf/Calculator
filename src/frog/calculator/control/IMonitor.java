package frog.calculator.control;

import frog.calculator.express.IExpression;

public interface IMonitor {

    void handle(IExpression expression);

}
