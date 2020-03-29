package frog.calculator.execute;

import frog.calculator.express.IExpression;
import frog.calculator.execute.space.ISpace;
import frog.calculator.math.number.BaseNumber;

public interface IOperator {

    ISpace<BaseNumber> operate(IExpression exp);

}
