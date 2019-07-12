package frog.calculator.operater.oprimpl.dimpl;

import frog.calculator.express.IExpression;
import frog.calculator.express.result.AResultExpression;

public class DoubleResultExpression extends AResultExpression {

    private double doubleValue;

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
        super.strValue = String.valueOf(doubleValue);
    }

    @Override
    public IExpression clone() {
        return super.clone();
    }

    @Override
    public String symbol() {
        return strValue;
    }

}
