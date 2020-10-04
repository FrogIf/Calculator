package frog.calculator.execute.base;

import frog.calculator.execute.AbstractMiddleOpr;
import frog.calculator.execute.exception.IncorrectStructureException;
import frog.calculator.execute.space.ISpace;
import frog.calculator.execute.util.ILeftRightMapDealer;
import frog.calculator.execute.util.OperateUtil;
import frog.calculator.express.IExpression;
import frog.calculator.math.number.BaseNumber;

public class DivOpr extends AbstractMiddleOpr {

    private static final DivMapDealer dealer = new DivMapDealer();

    @Override
    protected ISpace<BaseNumber> eval(IExpression left, IExpression right) {
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
