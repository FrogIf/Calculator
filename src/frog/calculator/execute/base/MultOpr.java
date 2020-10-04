package frog.calculator.execute.base;

import frog.calculator.execute.AbstractMiddleOpr;
import frog.calculator.execute.exception.IncorrectStructureException;
import frog.calculator.execute.space.ISpace;
import frog.calculator.execute.util.ILeftRightMapDealer;
import frog.calculator.execute.util.OperateUtil;
import frog.calculator.express.IExpression;
import frog.calculator.math.number.BaseNumber;

public class MultOpr extends AbstractMiddleOpr {

    private static final MapMultDealer dealer = new MapMultDealer();

    @Override
    protected ISpace<BaseNumber> eval(IExpression left, IExpression right) {
        if(left == null || right == null){
            throw new IncorrectStructureException("mult", "left : " + left + ", right : " + right);
        }
        ISpace<BaseNumber> leftInter = left.interpret();
        ISpace<BaseNumber> rightInter = right.interpret();
        return OperateUtil.transform(leftInter, rightInter, dealer);
    }

    public static class MapMultDealer implements ILeftRightMapDealer{

        @Override
        public BaseNumber deal(BaseNumber left, BaseNumber right) {
            return left.mult(right);
        }
    }
}
