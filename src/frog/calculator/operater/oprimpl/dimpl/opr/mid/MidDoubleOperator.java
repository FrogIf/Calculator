package frog.calculator.operater.oprimpl.dimpl.opr.mid;

import frog.calculator.express.IExpression;
import frog.calculator.express.mid.MidExpression;
import frog.calculator.express.result.AResultExpression;
import frog.calculator.operater.oprimpl.dimpl.opr.util.DoubleOperatorUtil;
import frog.calculator.operater.oprimpl.dimpl.IDoubleOperator;

public abstract class MidDoubleOperator implements IDoubleOperator {

    protected abstract double calculate(double left, double right);

    @Override
    public AResultExpression operate(IExpression expression) {
        MidExpression midExpression = (MidExpression) expression;
        AResultExpression leftResult = midExpression.getLeft().interpret();
        AResultExpression rightResult = midExpression.getRight().interpret();

        double leftDoubleValue = DoubleOperatorUtil.resultExpressionToDouble(leftResult);
        double rightDoubleValue = DoubleOperatorUtil.resultExpressionToDouble(rightResult);

        double result = calculate(leftDoubleValue, rightDoubleValue);

        return DoubleOperatorUtil.doubleToResultExpression(result);
    }

}
