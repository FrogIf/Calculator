package frog.calculator.operater.oprimpl.dimpl.opr.end;

import frog.calculator.express.IExpression;
import frog.calculator.operater.IOperator;
import frog.calculator.operater.oprimpl.dimpl.opr.util.DoubleOperatorUtil;

public class NumberDoubleOperator implements IOperator {

    @Override
    public IExpression operate(String symbol, IExpression... expression) {
        double value = Double.parseDouble(symbol);
        return DoubleOperatorUtil.doubleToResultExpression(value);
    }

}
