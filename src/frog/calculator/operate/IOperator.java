package frog.calculator.operate;

import frog.calculator.express.IExpression;
import frog.calculator.express.result.AResultExpression;

public interface IOperator<E extends IExpression> {

    AResultExpression operate(E expression);

    IOperator copyThis();

}
