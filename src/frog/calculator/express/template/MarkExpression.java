package frog.calculator.express.template;

import frog.calculator.express.IExpression;
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

    @Override
    public IExpression clone() {
        return super.clone();
    }
}
