package frog.calculator.operator.funs;

import frog.calculator.exception.UnrightExpressionException;
import frog.calculator.express.IExpression;
import frog.calculator.math.BaseNumber;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.operator.util.IOneElementDealer;
import frog.calculator.operator.util.OperateUtil;
import frog.calculator.space.ISpace;

public class SqrtOperator extends AbstractOperator {

    private static final SqrtDealer dealer = new SqrtDealer();

    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        IExpression child = exp.nextChild();
        if(child == null){
            throw new UnrightExpressionException();
        }else{
            return OperateUtil.transform(child.interpret(), dealer);
        }
    }

    private static class SqrtDealer implements IOneElementDealer{

        @Override
        public BaseNumber deal(BaseNumber num) {
            if(num != null){
                // TODO 改成无理数
                return num;
            }
            return null;
        }
    }
}
