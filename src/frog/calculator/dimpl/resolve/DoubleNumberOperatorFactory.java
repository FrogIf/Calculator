package frog.calculator.dimpl.resolve;

import frog.calculator.operate.IOperator;
import frog.calculator.dimpl.operate.opr.end.NumberDoubleOperator;
import frog.calculator.resolve.resolver.INumberOperatorFactory;

public class DoubleNumberOperatorFactory implements INumberOperatorFactory {

    private NumberDoubleOperator numberDoubleOperator = new NumberDoubleOperator();

    @Override
    public IOperator createNumberOperator() {
        return numberDoubleOperator;
    }
}
