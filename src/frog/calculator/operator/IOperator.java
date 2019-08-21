package frog.calculator.operator;

import frog.calculator.express.IExpression;
import frog.calculator.space.ISpace;

public interface IOperator {

    ISpace operate(IExpression exp);

}
