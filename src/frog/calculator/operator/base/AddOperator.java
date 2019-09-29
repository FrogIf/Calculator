package frog.calculator.operator.base;

import frog.calculator.exception.UnrightExpressionException;
import frog.calculator.express.IExpression;
import frog.calculator.math.INumber;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.operator.util.ILeftRightMapDealer;
import frog.calculator.operator.util.OperateUtil;
import frog.calculator.space.ISpace;

public class AddOperator extends AbstractOperator {

    private static final ILeftRightMapDealer addDealer = new AddDealer();

    @Override
    public ISpace<INumber> operate(IExpression exp) {
        IExpression left = exp.nextChild();
        IExpression right = exp.nextChild();

        if(right == null){
            throw new UnrightExpressionException();
        }

        if(left == null){
            return right.interpret();
        }else{
            ISpace<INumber> ls = left.interpret();
            ISpace<INumber> rs = right.interpret();

            return OperateUtil.transform(ls, rs, addDealer);
        }
    }

    private static class AddDealer implements ILeftRightMapDealer {
        @Override
        public INumber deal(INumber ln, INumber rn) {
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
