package frog.calculator.operator.base;

import frog.calculator.operator.exception.IncorrectStructureException;
import frog.calculator.express.IExpression;
import frog.calculator.math.BaseNumber;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.operator.util.ILeftRightMapDealer;
import frog.calculator.operator.util.OperateUtil;
import frog.calculator.space.ISpace;

public class MultOpr extends AbstractOperator {

    private static final MapMultDealer dealer = new MapMultDealer();

    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        IExpression left = exp.nextChild();
        IExpression right = exp.nextChild();
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
