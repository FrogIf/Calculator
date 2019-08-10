package frog.calculator.dimpl.opr.fun;

import frog.calculator.dimpl.opr.AbstractOperator;
import frog.calculator.express.IExpression;
import frog.calculator.operator.IOperator;
import frog.calculator.dimpl.opr.util.DoubleOperatorUtil;

public class SqrtOperator extends AbstractOperator {
    @Override
    public IExpression operate(String symbol, IExpression[] expressions) {
        if(expressions.length != 2){
            throw new IllegalArgumentException("can operate.");
        }
        IExpression exp = expressions[0];
        double sqrt = Math.sqrt(DoubleOperatorUtil.resultExpressionToDouble(exp.interpret()));
        return DoubleOperatorUtil.doubleToResultExpression(sqrt);
    }
}