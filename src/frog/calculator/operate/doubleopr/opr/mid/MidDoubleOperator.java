package frog.calculator.operate.doubleopr.opr.mid;

import frog.calculator.express.mid.MidExpression;
import frog.calculator.express.result.ResultExpression;
import frog.calculator.operate.IOperator;
import frog.calculator.operate.doubleopr.DoubleOperatorUtil;
import frog.calculator.operate.doubleopr.IDoubleOperator;

public abstract class MidDoubleOperator<E extends MidExpression> implements IDoubleOperator<E> {

    protected abstract double calculate(double left, double right);

    @Override
    public ResultExpression operate(E expression) {
        ResultExpression leftResult = expression.getLeft().interpret();
        ResultExpression rightResult = expression.getRight().interpret();

        double leftDoubleValue = DoubleOperatorUtil.resultExpressionToDouble(leftResult);
        double rightDoubleValue = DoubleOperatorUtil.resultExpressionToDouble(rightResult);

        double result = calculate(leftDoubleValue, rightDoubleValue);

        return DoubleOperatorUtil.doubleToResultExpression(result);
    }

    @Override
    public IOperator copyThis() {
        return this;
    }

}
