package frog.calculator.dimpl.opr;

import frog.calculator.dimpl.opr.util.DoubleOperatorUtil;
import frog.calculator.express.IExpression;
import frog.calculator.operator.IOperator;

public class AvgOperator extends AbstractOperator {
    @Override
    public IExpression operate(String symbol, IExpression... expressions) {
        if(expressions.length == 0){
            throw new IllegalArgumentException("arg is empty");
        }
        double sum = DoubleOperatorUtil.resultExpressionToDouble(expressions[0].interpret());
        for(int i = 1; i < expressions.length; i++){
            double result = DoubleOperatorUtil.resultExpressionToDouble(expressions[i].interpret());
            sum += result;
        }

        return DoubleOperatorUtil.doubleToResultExpression(sum / expressions.length);
    }
}
