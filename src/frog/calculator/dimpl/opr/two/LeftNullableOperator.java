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
        }else if(left != null){
            return this.doubleCalculate(left.interpret(), right.interpret());
        }else{
            // TODO 返回值
            return null;
        }
//        double leftNum = left == null ? 0 : DoubleOperatorUtil.resultExpressionToDouble(null);
//        if(right == null){
//            throw new IllegalArgumentException("right is null.");
//        }else{
//            ISpace rightResult = right.interpret();
//            return null;
//        }
    }
}
