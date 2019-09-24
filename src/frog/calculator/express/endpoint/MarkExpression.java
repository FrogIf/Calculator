package frog.calculator.express.endpoint;

import frog.calculator.math.INumber;
import frog.calculator.space.ISpace;

public class MarkExpression extends EndPointExpression {

    public MarkExpression(String symbol) {
        super(symbol, null);
    }

    @Override
    public ISpace<INumber> interpret() {
        throw new IllegalStateException("access illegal.");
    }

}
