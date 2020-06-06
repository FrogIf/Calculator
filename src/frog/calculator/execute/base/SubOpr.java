package frog.calculator.execute.base;

import frog.calculator.execute.AbstractMiddleOpr;
import frog.calculator.execute.space.ISpace;
import frog.calculator.execute.util.ILeftRightMapDealer;
import frog.calculator.execute.util.IOneElementDealer;
import frog.calculator.execute.util.OperateUtil;
import frog.calculator.express.IExpression;
import frog.calculator.math.number.BaseNumber;

public class SubOpr extends AbstractMiddleOpr {

    private static final ILeftRightMapDealer subDealer = new SubDealer();

    private static final IOneElementDealer notDealer = new NotDealer();

    @Override
    protected ISpace<BaseNumber> exec(IExpression left, IExpression right) {
        if(left == null){
            ISpace<BaseNumber> rightSpace = right.interpret();
            return OperateUtil.transform(rightSpace, notDealer);
        }else{
            ISpace<BaseNumber> ls = left.interpret();
            ISpace<BaseNumber> rs = right.interpret();
            return OperateUtil.transform(ls, rs, subDealer);
        }
    }

    private static class NotDealer implements IOneElementDealer{

        @Override
        public BaseNumber deal(BaseNumber num) {
            if(num != null){
                return BaseNumber.ZERO.sub(num);
            }
            return null;
        }
    }

    private static class SubDealer implements ILeftRightMapDealer {
        @Override
        public BaseNumber deal(BaseNumber ln, BaseNumber rn) {
            if(ln != null || rn != null){
                if(ln == null){
                    return rn;
                }else if(rn == null){
                    return ln;
                }else{
                    return ln.sub(rn);
                }
            }
            return null;
        }
    }
}
