package frog.calculator.exec.fun;

import frog.calculator.exec.exception.IncorrectStructureException;
import frog.calculator.express.IExpression;
import frog.calculator.math.BaseNumber;
import frog.calculator.math.MathUtil;
import frog.calculator.exec.AbstractOperator;
import frog.calculator.exec.util.IOneElementDealer;
import frog.calculator.exec.util.OperateUtil;
import frog.calculator.exec.space.ISpace;

public class SqrtOpr extends AbstractOperator {

    private static final SqrtDealer dealer = new SqrtDealer();

    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        IExpression child = exp.nextChild();
        if(child == null){
            throw new IncorrectStructureException("sqrt", "child is null.");
        }else{
            return OperateUtil.transform(child.interpret(), dealer);
        }
    }

    private static class SqrtDealer implements IOneElementDealer{

        @Override
        public BaseNumber deal(BaseNumber num) {
            if(num != null){
                return MathUtil.sqrt(num);
            }
            return null;
        }

    }
}
