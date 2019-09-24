package frog.calculator.operator.base;

import frog.calculator.express.IExpression;
import frog.calculator.math.INumber;
import frog.calculator.math.NumberParsor;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.space.*;

public class NumberOperator extends AbstractOperator {
    @Override
    public ISpace<INumber> operate(IExpression exp) {
        INumber number = NumberParsor.parseNumber(exp.symbol());
        return new AtomSpace<>(number);
    }
}
