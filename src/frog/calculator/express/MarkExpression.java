package frog.calculator.express;

import frog.calculator.express.EndPointExpression;
import frog.calculator.math.BaseNumber;
import frog.calculator.space.ISpace;

public class MarkExpression extends EndPointExpression {

    public MarkExpression(String symbol) {
        super(symbol, null);
    }

    @Override
    public ISpace<BaseNumber> interpret() {
        throw new IllegalStateException("access illegal.");
    }

}
