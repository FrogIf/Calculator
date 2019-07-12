package frog.calculator.operater;

import frog.calculator.express.IExpression;
import frog.calculator.express.result.AResultExpression;

public interface IOperator {

    AResultExpression operate(IExpression expression);

}
