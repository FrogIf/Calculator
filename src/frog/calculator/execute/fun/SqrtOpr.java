package frog.calculator.execute.fun;

import frog.calculator.execute.AbstractSingleArgOpr;
import frog.calculator.execute.exception.IncorrectStructureException;
import frog.calculator.execute.space.ISpace;
import frog.calculator.execute.util.IOneElementDealer;
import frog.calculator.execute.util.OperateUtil;
import frog.calculator.express.IExpression;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.math.tool.MathUtil;

public class SqrtOpr extends AbstractSingleArgOpr {

    private static final SqrtDealer dealer = new SqrtDealer();

    @Override
    protected ISpace<BaseNumber> eval(IExpression child) {
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
