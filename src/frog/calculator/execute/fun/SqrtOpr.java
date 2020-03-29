package frog.calculator.execute.fun;

import frog.calculator.execute.exception.IncorrectStructureException;
import frog.calculator.express.IExpression;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.math.tool.MathUtil;
import frog.calculator.execute.AbstractOperator;
import frog.calculator.execute.util.IOneElementDealer;
import frog.calculator.execute.util.OperateUtil;
import frog.calculator.execute.space.ISpace;

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
