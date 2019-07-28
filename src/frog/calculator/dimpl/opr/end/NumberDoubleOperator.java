package frog.calculator.dimpl.opr.end;

import frog.calculator.dimpl.opr.AbstractOperator;
import frog.calculator.express.IExpression;
import frog.calculator.operator.IOperator;
import frog.calculator.dimpl.opr.util.DoubleOperatorUtil;

public class NumberDoubleOperator extends AbstractOperator {

    @Override
    public IExpression operate(String symbol, IExpression... expression) {
        double value = Double.parseDouble(symbol);
        return DoubleOperatorUtil.doubleToResultExpression(value);
    }

}
