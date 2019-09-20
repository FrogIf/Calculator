package frog.calculator.operator;

import frog.calculator.express.IExpression;
import frog.calculator.math.INumber;
import frog.calculator.space.IPoint;
import frog.calculator.space.ISpace;

public interface IOperator {

    ISpace<IPoint<INumber>> operate(IExpression exp);

}
