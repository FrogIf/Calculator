package frog.calculator.operator.base;

import frog.calculator.express.IExpression;
import frog.calculator.math.BaseNumber;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.operator.util.ILeftRightMapDealer;
import frog.calculator.operator.util.IOneElementDealer;
import frog.calculator.operator.util.OperateUtil;
import frog.calculator.space.ISpace;

public class SubOpr extends AbstractOperator {

    private static final ILeftRightMapDealer subDealer = new SubDealer();

    private static final IOneElementDealer notDealer = new NotDealer();

    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        IExpression left = exp.nextChild();
        IExpression right = exp.nextChild();

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
