package frog.calculator.dimpl.operate.opr.end;

import frog.calculator.express.end.NumberExpression;
import frog.calculator.express.result.AResultExpression;
import frog.calculator.dimpl.operate.util.DoubleOperatorUtil;

public class NumberDoubleOperator<E extends NumberExpression> extends EndDoubleOperator<E> {

    @Override
    public AResultExpression operate(E expression) {
        String number = expression.symbol();

        double value = Double.parseDouble(number);

        AResultExpression resultExpression = DoubleOperatorUtil.doubleToResultExpression(value);

        return resultExpression;
    }
}
