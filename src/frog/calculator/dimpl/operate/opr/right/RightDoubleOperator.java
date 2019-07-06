package frog.calculator.dimpl.operate.opr.right;

import frog.calculator.express.IExpression;
import frog.calculator.express.result.AResultExpression;
import frog.calculator.express.right.RightExpression;
import frog.calculator.operate.IOperator;
import frog.calculator.dimpl.operate.util.DoubleOperatorUtil;
import frog.calculator.dimpl.operate.IDoubleOperator;

public abstract class RightDoubleOperator<T extends RightExpression> implements IDoubleOperator<T> {

    protected abstract double calculate(double left);

    @Override
    public AResultExpression operate(T expression) {
        IExpression left = expression.getLeft();
        AResultExpression leftResult = left.interpret();

        double leftResultValue = DoubleOperatorUtil.resultExpressionToDouble(leftResult);

        double result = this.calculate(leftResultValue);

        return DoubleOperatorUtil.doubleToResultExpression(result);
    }

    @Override
    public IOperator copyThis() {
        return this;
    }
}
