package frog.calculator.express;

import frog.calculator.math.number.BaseNumber;
import frog.calculator.execute.space.ISpace;

public class MarkExpression extends EndPointExpression {

    public MarkExpression(String symbol) {
        super(symbol, null);
    }

    @Override
    public ISpace<BaseNumber> interpret() {
        throw new IllegalStateException("access illegal.");
    }
}
