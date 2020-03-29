package frog.calculator.execute.base;

import frog.calculator.execute.exception.IncorrectStructureException;
import frog.calculator.express.IExpression;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.execute.AbstractOperator;
import frog.calculator.execute.util.ILeftRightMapDealer;
import frog.calculator.execute.util.OperateUtil;
import frog.calculator.execute.space.ISpace;

public class DivOpr extends AbstractOperator {

    private static final DivMapDealer dealer = new DivMapDealer();

    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        IExpression left = exp.nextChild();
        IExpression right = exp.nextChild();
        if(left == null || right == null){
            throw new IncorrectStructureException("div", "left : " + left + ", right : " + right);
        }

        return OperateUtil.transform(left.interpret(), right.interpret(), dealer);
    }

    private static class DivMapDealer implements ILeftRightMapDealer{

        @Override
        public BaseNumber deal(BaseNumber left, BaseNumber right) {
            return left.div(right);
        }
    }
}
