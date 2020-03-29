package frog.calculator.execute.ext;

import frog.calculator.express.IExpression;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.math.tool.MathUtil;
import frog.calculator.execute.AbstractOperator;
import frog.calculator.execute.util.IOneElementDealer;
import frog.calculator.execute.util.OperateUtil;
import frog.calculator.execute.space.ISpace;

public class FactorialOpr extends AbstractOperator {

    private static final FactorialDealer dealer = new FactorialDealer();

    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        IExpression child = exp.nextChild();
        ISpace<BaseNumber> space = child.interpret();
        return OperateUtil.transform(space, dealer);
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
