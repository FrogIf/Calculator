package frog.calculator.operator;

import frog.calculator.express.IExpression;
import frog.calculator.math.INumber;
import frog.calculator.space.ISpace;

public interface IOperator {

    ISpace<INumber> operate(IExpression exp);

}
