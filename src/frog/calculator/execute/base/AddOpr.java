package frog.calculator.execute.base;

import frog.calculator.execute.AbstractMiddleOpr;
import frog.calculator.execute.exception.IncorrectStructureException;
import frog.calculator.execute.space.ISpace;
import frog.calculator.execute.util.ILeftRightMapDealer;
import frog.calculator.execute.util.OperateUtil;
import frog.calculator.express.IExpression;
import frog.calculator.math.number.BaseNumber;

public class AddOpr extends AbstractMiddleOpr {

    private static final ILeftRightMapDealer addDealer = new AddDealer();

    @Override
    protected ISpace<BaseNumber> exec(IExpression left, IExpression right) {
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
