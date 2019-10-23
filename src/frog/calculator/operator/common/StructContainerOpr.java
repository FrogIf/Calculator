package frog.calculator.operator.common;

import frog.calculator.express.IExpression;
import frog.calculator.math.BaseNumber;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.space.ISpace;

public class StructContainerOpr extends AbstractOperator {

    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        return exp.nextChild().interpret();
    }

}
