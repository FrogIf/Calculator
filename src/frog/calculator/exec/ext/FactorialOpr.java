package frog.calculator.exec.ext;

import frog.calculator.express.IExpression;
import frog.calculator.math.BaseNumber;
import frog.calculator.math.MathUtil;
import frog.calculator.exec.AbstractOperator;
import frog.calculator.exec.util.IOneElementDealer;
import frog.calculator.exec.util.OperateUtil;
import frog.calculator.exec.space.ISpace;

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
