package frog.calculator.operate.doubleopr.oprs.right;

import frog.calculator.express.IExpression;
import frog.calculator.express.result.ResultExpression;
import frog.calculator.express.right.RightExpression;
import frog.calculator.operate.doubleopr.DoubleOperatorUtil;
import frog.calculator.operate.doubleopr.IDoubleOperator;

public abstract class RightDoubleOperator<T extends RightExpression> implements IDoubleOperator<T> {

    protected abstract double calculate(double left);

    @Override
    public ResultExpression operate(T expression) {
        IExpression left = expression.getLeft();
        ResultExpression leftResult = left.interpret();

        double leftResultValue = DoubleOperatorUtil.resultExpressionToDouble(leftResult);

        double result = this.calculate(leftResultValue);

        return DoubleOperatorUtil.doubleToResultExpression(result);
    }
}
