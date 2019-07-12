package frog.calculator.operater.oprimpl.strimpl;

import frog.calculator.express.result.AResultExpression;

public class StringResultExpression extends AResultExpression {

    public StringResultExpression(String strValue) {
        super.strValue = strValue;
    }

    @Override
    public String symbol() {
        return super.strValue;
    }
}
