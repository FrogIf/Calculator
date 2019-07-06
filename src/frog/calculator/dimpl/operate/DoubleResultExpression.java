package frog.calculator.dimpl.operate;

import frog.calculator.express.IExpression;
import frog.calculator.express.result.AResultExpression;
import frog.calculator.operate.IOperator;

public class DoubleResultExpression extends AResultExpression {

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
    public AResultExpression interpret() {
        return this;
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public IExpression clone() {
        return super.clone();
    }

    @Override
    public String symbol() {
        // TODO 需要确定这里应该返回什么
        return null;
    }

    @Override
    public IOperator getOperator() {
        return null;
    }

    @Override
    public String resultValue() {
        return this.strValue;
    }
}
