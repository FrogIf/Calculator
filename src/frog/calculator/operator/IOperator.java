package frog.calculator.operator;

import frog.calculator.express.IExpression;
import frog.calculator.space.ISpace;
import frog.calculator.math.BaseNumber;

public interface IOperator {

    ISpace<BaseNumber> operate(IExpression exp);

}
