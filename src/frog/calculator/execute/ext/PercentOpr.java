package frog.calculator.execute.ext;

import frog.calculator.express.IExpression;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.execute.AbstractOperator;
import frog.calculator.execute.util.IOneElementDealer;
import frog.calculator.execute.util.OperateUtil;
import frog.calculator.execute.space.ISpace;

public class PercentOpr extends AbstractOperator {

    private static final BaseNumber ONE_HUNDRED = BaseNumber.valueOf(100);

    private static final PercentDealer dealer = new PercentDealer();

    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        IExpression child = exp.nextChild();
        return OperateUtil.transform(child.interpret(), dealer);
    }

    private static class PercentDealer implements IOneElementDealer{

        @Override
        public BaseNumber deal(BaseNumber num) {
            if(num != null){
                return num.div(ONE_HUNDRED);
            }
            return null;
        }
    }
}
