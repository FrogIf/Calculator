package frog.calculator.dimpl.operate.opr.mid;

import frog.calculator.express.mid.MidExpression;
import frog.calculator.express.result.AResultExpression;
import frog.calculator.operate.IOperator;
import frog.calculator.dimpl.operate.util.DoubleOperatorUtil;
import frog.calculator.dimpl.operate.IDoubleOperator;

public abstract class MidDoubleOperator<E extends MidExpression> implements IDoubleOperator<E> {

    protected abstract double calculate(double left, double right);

    @Override
    public AResultExpression operate(E expression) {
        AResultExpression leftResult = expression.getLeft().interpret();
        AResultExpression rightResult = expression.getRight().interpret();

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
