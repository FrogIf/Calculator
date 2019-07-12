package frog.calculator.operater.oprimpl.dimpl.opr.end;

import frog.calculator.operater.oprimpl.dimpl.opr.util.DoubleOperatorUtil;
import frog.calculator.express.IExpression;
import frog.calculator.express.result.AResultExpression;

public class NumberDoubleOperator extends EndDoubleOperator {

    @Override
    public AResultExpression operate(IExpression expression) {
        String number = expression.symbol();

        double value = Double.parseDouble(number);

        AResultExpression resultExpression = DoubleOperatorUtil.doubleToResultExpression(value);

        return resultExpression;
    }
}
