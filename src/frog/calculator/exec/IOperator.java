package frog.calculator.exec;

import frog.calculator.express.IExpression;
import frog.calculator.exec.space.ISpace;
import frog.calculator.math.BaseNumber;

public interface IOperator {

    ISpace<BaseNumber> operate(IExpression exp);

}
