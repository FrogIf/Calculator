package frog.calculator.operator.common;

import frog.calculator.express.IExpression;
import frog.calculator.math.INumber;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.operator.IOperator;
import frog.calculator.space.IPoint;
import frog.calculator.space.ISpace;

public class StructContainerOperator extends AbstractOperator {

    @Override
    public ISpace<IPoint<INumber>> operate(IExpression exp) {
        return exp.nextChild().interpret();
    }

}
