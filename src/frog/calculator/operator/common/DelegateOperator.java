package frog.calculator.operator.common;

import frog.calculator.express.IExpression;
import frog.calculator.express.container.CustomFunctionExpression;
import frog.calculator.math.BaseNumber;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.space.ISpace;

public class DelegateOperator extends AbstractOperator {
    @Override
    public ISpace<BaseNumber> operate(IExpression expression) {
        IExpression func = expression.nextChild();
        IExpression body = expression.nextChild();

        if(func instanceof CustomFunctionExpression){
            CustomFunctionExpression cf = (CustomFunctionExpression) func;
            cf.delegate(body);
        }else{
            throw new IllegalArgumentException("can't support expression.");
        }

        return null;
    }
}
