package frog.calculator.dimpl.opr.single;

import frog.calculator.dimpl.opr.AbstractOperator;
import frog.calculator.express.IExpression;
import frog.calculator.space.ISpace;

public abstract class SingleArgOperator extends AbstractOperator {

    protected abstract double doubleCalcuate(double arg);

    @Override
    public ISpace operate(IExpression exp) {
        return null;
    }
}
