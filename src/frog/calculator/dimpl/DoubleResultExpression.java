package frog.calculator.dimpl;

import frog.calculator.express.EndPointExpression;
import frog.calculator.operator.IOperator;
import frog.calculator.space.ISpace;

public class DoubleResultExpression extends EndPointExpression {

    private double doubleValue;

    public DoubleResultExpression(String symbol, IOperator operator) {
        super(symbol, operator);
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    @Override
    public ISpace interpret() {
        return null;
    }
}
