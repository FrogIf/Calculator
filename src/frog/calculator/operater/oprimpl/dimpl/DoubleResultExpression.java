package frog.calculator.operater.oprimpl.dimpl;

import frog.calculator.express.endpoint.EndPointExpression;
import frog.calculator.operater.IOperator;

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

}
