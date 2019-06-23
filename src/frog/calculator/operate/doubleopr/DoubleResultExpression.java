package frog.calculator.operate.doubleopr;

import frog.calculator.express.result.ResultExpression;

public class DoubleResultExpression extends ResultExpression {

    private double doubleValue;

    private String strValue;

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
        this.strValue = String.valueOf(doubleValue);
    }

    @Override
    public ResultExpression interpret() {
        return this;
    }

    @Override
    public String resultValue() {
        return this.strValue;
    }
}
