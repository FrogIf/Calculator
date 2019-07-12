package frog.calculator.operater.oprimpl.dimpl.opr.right;

import frog.calculator.operater.oprimpl.dimpl.IDoubleOperator;
import frog.calculator.operater.oprimpl.dimpl.opr.util.DoubleOperatorUtil;
import frog.calculator.express.IExpression;
import frog.calculator.express.result.AResultExpression;
import frog.calculator.express.right.RightExpression;

public abstract class RightDoubleOperator implements IDoubleOperator {

    protected abstract double calculate(double left);

    @Override
    public AResultExpression operate(IExpression expression) {
        RightExpression rightExpression = (RightExpression) expression;
        IExpression left = rightExpression.getLeft();
        AResultExpression leftResult = left.interpret();

        double leftResultValue = DoubleOperatorUtil.resultExpressionToDouble(leftResult);

        double result = this.calculate(leftResultValue);

        return DoubleOperatorUtil.doubleToResultExpression(result);
    }

}
