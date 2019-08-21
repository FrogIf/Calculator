package frog.calculator.dimpl.opr.two;

import frog.calculator.dimpl.opr.AbstractOperator;
import frog.calculator.exception.StructureErrorException;
import frog.calculator.express.IExpression;
import frog.calculator.space.ISpace;

public abstract class TwoArgOperator extends AbstractOperator {

    protected abstract ISpace doubleCalculate(ISpace left, ISpace right);

    @Override
    public ISpace operate(IExpression exp) {
        IExpression left = exp.nextChild();
        IExpression right = exp.nextChild();

        if(exp.hasNextChild()){
            throw new StructureErrorException(exp.symbol(), exp.order());
        }

        ISpace leftResult = left.interpret();
        ISpace rightResult = right.interpret();

        return this.doubleCalculate(leftResult, rightResult);
    }


}
