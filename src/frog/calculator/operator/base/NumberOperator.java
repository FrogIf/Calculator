package frog.calculator.operator.base;

import frog.calculator.express.IExpression;
import frog.calculator.math.BaseNumber;
import frog.calculator.math.NumberParser;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.space.AtomSpace;
import frog.calculator.space.ISpace;

public class NumberOperator extends AbstractOperator {
    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        BaseNumber number = NumberParser.parseNumber(exp.symbol());
        return new AtomSpace<>(number);
    }
}
