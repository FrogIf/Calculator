package frog.calculator.dimpl.opr.fun;

import frog.calculator.dimpl.opr.AbstractOperator;
import frog.calculator.dimpl.opr.util.DoubleOperatorUtil;
import frog.calculator.express.IExpression;
import frog.calculator.operator.IOperator;

public class MaxOperator extends AbstractOperator {
    @Override
    public IExpression operate(String symbol, IExpression[] expressions) {
        double max = DoubleOperatorUtil.resultExpressionToDouble(expressions[0].interpret());
        for(int i = 1; i < expressions.length; i++){
            double result = DoubleOperatorUtil.resultExpressionToDouble(expressions[i].interpret());
            if(result > max){
                max = result;
            }
        }

        return DoubleOperatorUtil.doubleToResultExpression(max);
    }
}
