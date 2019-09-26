package frog.calculator.operator.base;

import frog.calculator.express.IExpression;
import frog.calculator.math.INumber;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.operator.util.ISingleElementDealer;
import frog.calculator.operator.util.OperateUtil;
import frog.calculator.space.ISpace;

public class SubOperator extends AbstractOperator {

    private static final ISingleElementDealer subDealer = new SubDealer();

    @Override
    public ISpace<INumber> operate(IExpression exp) {
        IExpression left = exp.nextChild();
        IExpression right = exp.nextChild();

        if(left == null){

        }else{
            ISpace<INumber> ls = left.interpret();
            ISpace<INumber> rs = right.interpret();
            return OperateUtil.operate(ls, rs, subDealer);
        }

        return null;
    }

    private static class SubDealer implements ISingleElementDealer{

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
