package frog.calculator.execute.ext;

import frog.calculator.execute.AbstractSingleArgOpr;
import frog.calculator.execute.space.ISpace;
import frog.calculator.execute.util.IOneElementDealer;
import frog.calculator.execute.util.OperateUtil;
import frog.calculator.express.IExpression;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.math.tool.MathUtil;

public class FactorialOpr extends AbstractSingleArgOpr {

    private static final FactorialDealer dealer = new FactorialDealer();

    @Override
    protected ISpace<BaseNumber> exec(IExpression child) {
        return OperateUtil.transform(child.interpret(), dealer);
    }

    private static class FactorialDealer implements IOneElementDealer{

        @Override
        public BaseNumber deal(BaseNumber num) {
            if(num != null){
                return MathUtil.factorial(num);
            }
            return null;
        }
    }

}
