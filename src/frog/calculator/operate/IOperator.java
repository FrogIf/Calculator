package frog.calculator.operate;

import frog.calculator.express.IExpression;
import frog.calculator.express.result.ResultExpression;

public interface IOperator<E extends IExpression> {

    ResultExpression operate(E expression);

}
