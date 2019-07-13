package frog.calculator.dimpl.opr.two;

import frog.calculator.dimpl.opr.util.DoubleOperatorUtil;
import frog.calculator.express.IExpression;

public abstract class LeftNullableOperator extends TwoArgOperator {

    @Override
    public IExpression operate(String symbol, IExpression... expressions) {
        IExpression left = expressions[0];
        IExpression right = expressions[1];

        double leftNum = left == null ? 0 : DoubleOperatorUtil.resultExpressionToDouble(left.interpret());
        if(right == null){
            throw new IllegalArgumentException("right is null.");
        }else{
            IExpression rightResult = right.interpret();
            double result = this.doubleCalculate(leftNum, DoubleOperatorUtil.resultExpressionToDouble(rightResult));
            return DoubleOperatorUtil.doubleToResultExpression(result);
        }
    }
}
