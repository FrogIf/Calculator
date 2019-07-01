package frog.calculator.operate.doubleopr.opr.end;

import frog.calculator.express.end.EndExpression;
import frog.calculator.operate.IOperator;
import frog.calculator.operate.doubleopr.IDoubleOperator;

public abstract class EndDoubleOperator<E extends EndExpression> implements IDoubleOperator<E> {

    @Override
    public IOperator copyThis() {
        return this;
    }
}
