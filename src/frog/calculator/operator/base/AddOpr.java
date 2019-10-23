package frog.calculator.operator.base;

import frog.calculator.exception.UnrightExpressionException;
import frog.calculator.express.IExpression;
import frog.calculator.math.BaseNumber;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.operator.util.ILeftRightMapDealer;
import frog.calculator.operator.util.OperateUtil;
import frog.calculator.space.ISpace;

public class AddOpr extends AbstractOperator {

    private static final ILeftRightMapDealer addDealer = new AddDealer();

    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        IExpression left = exp.nextChild();
        IExpression right = exp.nextChild();

        if(right == null){
            throw new UnrightExpressionException();
        }

        if(left == null){
            return right.interpret();
        }else{
            ISpace<BaseNumber> ls = left.interpret();
            ISpace<BaseNumber> rs = right.interpret();

            return OperateUtil.transform(ls, rs, addDealer);
        }
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
