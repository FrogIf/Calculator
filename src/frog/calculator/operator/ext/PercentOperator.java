package frog.calculator.operator.ext;

import frog.calculator.express.IExpression;
import frog.calculator.math.BaseNumber;
import frog.calculator.math.rational.RationalNumber;
import frog.calculator.math.real.PolynomialNumber;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.operator.util.IOneElementDealer;
import frog.calculator.operator.util.OperateUtil;
import frog.calculator.space.ISpace;

public class PercentOperator extends AbstractOperator {

    private static final BaseNumber ONE_HUNDRED
            = new BaseNumber(new PolynomialNumber(new RationalNumber("100"), null));

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
