package frog.calculator.operate.doubleopr.oprs.end;

import frog.calculator.express.end.NumberExpression;
import frog.calculator.express.result.ResultExpression;
import frog.calculator.operate.doubleopr.DoubleOperatorUtil;

public class NumberDoubleOperator<E extends NumberExpression> extends EndDoubleOperator<E> {

    @Override
    public ResultExpression operate(E expression) {
        String number = expression.number();

        double value = Double.parseDouble(number);

        ResultExpression resultExpression = DoubleOperatorUtil.doubleToResultExpression(value);

        return resultExpression;
    }
}
