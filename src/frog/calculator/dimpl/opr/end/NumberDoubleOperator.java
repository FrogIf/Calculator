package frog.calculator.dimpl.opr.end;

import frog.calculator.dimpl.opr.AbstractOperator;
import frog.calculator.dimpl.opr.util.DoubleOperatorUtil;
import frog.calculator.express.IExpression;

public class NumberDoubleOperator extends AbstractOperator {

    @Override
    public IExpression operate(String symbol, IExpression[] expression) {
        double value = Double.parseDouble(symbol);
        return DoubleOperatorUtil.doubleToResultExpression(value);
    }

}
