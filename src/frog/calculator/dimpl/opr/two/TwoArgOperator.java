package frog.calculator.dimpl.opr.two;

import frog.calculator.express.IExpression;
import frog.calculator.operater.IOperator;
import frog.calculator.dimpl.opr.util.DoubleOperatorUtil;

public abstract class TwoArgOperator implements IOperator {

    protected abstract double doubleCalculate(double left, double right);

    @Override
    public IExpression operate(String symbol, IExpression... expressions) {
        if(expressions.length != 2 || expressions[0] == null || expressions[1] == null){
            throw new IllegalArgumentException("input expressions' number is not right.");
        }

        IExpression left = expressions[0];
        IExpression right = expressions[1];

        IExpression leftResult = left.interpret();
        IExpression rightResult = right.interpret();

        double result = this.doubleCalculate(DoubleOperatorUtil.resultExpressionToDouble(leftResult), DoubleOperatorUtil.resultExpressionToDouble(rightResult));

        return DoubleOperatorUtil.doubleToResultExpression(result);
    }


}
