package frog.calculator.operator.base;

import frog.calculator.express.IExpression;
import frog.calculator.math.INumber;
import frog.calculator.math.RationalNumber;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.operator.util.ILeftRightMapDealer;
import frog.calculator.operator.util.IOneElementDealer;
import frog.calculator.operator.util.OperateUtil;
import frog.calculator.space.ISpace;

public class SubOperator extends AbstractOperator {

    private static final ILeftRightMapDealer subDealer = new SubDealer();

    private static final IOneElementDealer notDealer = new NotDealer();

    @Override
    public ISpace<INumber> operate(IExpression exp) {
        IExpression left = exp.nextChild();
        IExpression right = exp.nextChild();

        if(left == null){
            ISpace<INumber> rightSpace = right.interpret();
            return OperateUtil.transform(rightSpace, notDealer);
        }else{
            ISpace<INumber> ls = left.interpret();
            ISpace<INumber> rs = right.interpret();
            return OperateUtil.transform(ls, rs, subDealer);
        }
    }

    private static class NotDealer implements IOneElementDealer{

        @Override
        public INumber deal(INumber num) {
            if(num != null){
                return RationalNumber.ZERO.sub(num);
            }
            return null;
        }
    }

    private static class SubDealer implements ILeftRightMapDealer {
        @Override
        public INumber deal(INumber ln, INumber rn) {
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
