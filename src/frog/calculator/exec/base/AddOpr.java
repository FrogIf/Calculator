package frog.calculator.exec.base;

import frog.calculator.exec.exception.IncorrectStructureException;
import frog.calculator.express.IExpression;
import frog.calculator.math.BaseNumber;
import frog.calculator.exec.AbstractOperator;
import frog.calculator.exec.util.ILeftRightMapDealer;
import frog.calculator.exec.util.OperateUtil;
import frog.calculator.exec.space.ISpace;

public class AddOpr extends AbstractOperator {

    private static final ILeftRightMapDealer addDealer = new AddDealer();

    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        IExpression left = exp.nextChild();
        IExpression right = exp.nextChild();

        if(right == null){
            throw new IncorrectStructureException("add", "right child is null.");
        }

        if(left == null){
            return right.interpret();
        }else{
            ISpace<BaseNumber> ls = left.interpret();
            ISpace<BaseNumber> rs = right.interpret();

            return addSpace(ls, rs);
        }
    }

    public static ISpace<BaseNumber> addSpace(ISpace<BaseNumber> left, ISpace<BaseNumber> right){
        return OperateUtil.transform(left, right, addDealer);
    }

    private static class AddDealer implements ILeftRightMapDealer {
        @Override
        public BaseNumber deal(BaseNumber ln, BaseNumber rn) {
            if(ln != null || rn != null){
                if(ln == null){
                    return rn;
                }else if(rn == null){
                    return ln;
                }else{
                    return ln.add(rn);
                }
            }
            return null;
        }
    }
}
