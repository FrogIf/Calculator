package frog.calculator.express;

import frog.calculator.math.BaseNumber;
import frog.calculator.exec.space.ISpace;

public class MarkExpression extends EndPointExpression {

    public MarkExpression(String symbol) {
        super(symbol, null);
    }

    @Override
    public ISpace<BaseNumber> interpret() {
        throw new IllegalStateException("access illegal.");
    }

}
