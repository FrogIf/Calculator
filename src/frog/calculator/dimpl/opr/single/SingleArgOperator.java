package frog.calculator.dimpl.opr.single;

import frog.calculator.dimpl.opr.AbstractOperator;
import frog.calculator.express.IExpression;
import frog.calculator.operator.IOperator;
import frog.calculator.dimpl.opr.util.DoubleOperatorUtil;

public abstract class SingleArgOperator extends AbstractOperator {

    protected abstract double doubleCalcuate(double arg);

    @Override
    public IExpression operate(String symbol, IExpression... expressions) {
        if(expressions.length != 1 || expressions[0] == null){
            throw new IllegalArgumentException("input expressions' number is not right.");
        }else{
            IExpression exp = expressions[0];
            double value = DoubleOperatorUtil.resultExpressionToDouble(exp.interpret());

            double result = this.doubleCalcuate(value);

            return DoubleOperatorUtil.doubleToResultExpression(result);
        }
    }
}
