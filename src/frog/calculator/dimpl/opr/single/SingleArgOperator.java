package frog.calculator.dimpl.opr.single;

import frog.calculator.express.IExpression;
import frog.calculator.operater.IOperator;
import frog.calculator.dimpl.opr.util.DoubleOperatorUtil;

public abstract class SingleArgOperator implements IOperator {

    protected abstract double doubleCalcuate(double arg);

    @Override
    public IExpression operate(String symbol, IExpression... expressions) {
        IExpression exp;
        if(expressions.length == 1){
            exp = expressions[0];
        }else if(expressions.length == 2){
            exp = expressions[0] == null ? expressions[1] : expressions[0];
        }else{
            throw new IllegalArgumentException("input expressions' number is not right.");
        }

        double value = DoubleOperatorUtil.resultExpressionToDouble(exp.interpret());

        double result = this.doubleCalcuate(value);

        return DoubleOperatorUtil.doubleToResultExpression(result);
    }
}
