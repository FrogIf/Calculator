package frog.calculator.operator.ext;

import frog.calculator.exception.UnrightExpressionException;
import frog.calculator.express.IExpression;
import frog.calculator.math.BaseNumber;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.operator.util.IOneElementDealer;
import frog.calculator.operator.util.OperateUtil;
import frog.calculator.space.ISpace;

public class FactorialOperator extends AbstractOperator {

    private static final FactorialDealer dealer = new FactorialDealer();

    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        IExpression child = exp.nextChild();
        if(child == null){
            throw new UnrightExpressionException();
        }else{
            return OperateUtil.transform(child.interpret(), dealer);
        }
    }

    private static class FactorialDealer implements IOneElementDealer{

        @Override
        public BaseNumber deal(BaseNumber num) {
            if(num != null){
                // 暂时还算不了, 涉及到解析延拓, 积分, 超越函数
//                while(BaseNumber.ONE)
                return num;
            }
            return null;
        }
    }
}
