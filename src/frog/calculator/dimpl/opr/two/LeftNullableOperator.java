package frog.calculator.dimpl.opr.two;

import frog.calculator.express.IExpression;
import frog.calculator.space.ISpace;

public abstract class LeftNullableOperator extends TwoArgOperator {

    @Override
    public ISpace operate(IExpression expression) {
        IExpression left = expression.nextChild();
        IExpression right = expression.nextChild();

        if(right == null){
            throw new IllegalArgumentException("right is null.");
        }else{
            return this.doubleCalculate(left == null ? null : left.interpret(), right.interpret());
        }
    }
}
