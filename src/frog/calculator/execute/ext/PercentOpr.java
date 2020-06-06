package frog.calculator.execute.ext;

import frog.calculator.execute.AbstractSingleArgOpr;
import frog.calculator.execute.space.ISpace;
import frog.calculator.execute.util.IOneElementDealer;
import frog.calculator.execute.util.OperateUtil;
import frog.calculator.express.IExpression;
import frog.calculator.math.number.BaseNumber;

public class PercentOpr extends AbstractSingleArgOpr {

    private static final BaseNumber ONE_HUNDRED = BaseNumber.valueOf(100);

    private static final PercentDealer dealer = new PercentDealer();

    @Override
    protected ISpace<BaseNumber> exec(IExpression child) {
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
