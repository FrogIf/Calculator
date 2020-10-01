package frog.calculator.log;

import frog.calculator.express.IExpression;

public interface ICalLogger {

    void info(String msg, Object... obj);

    void debug(String msg, Object... obj);

    void expression(IExpression expression);

}
