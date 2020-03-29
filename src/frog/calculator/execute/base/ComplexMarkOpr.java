package frog.calculator.execute.base;

import frog.calculator.express.IExpression;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.math.tool.MathUtil;
import frog.calculator.execute.AbstractOperator;
import frog.calculator.execute.util.IOneElementDealer;
import frog.calculator.execute.util.OperateUtil;
import frog.calculator.execute.space.AtomSpace;
import frog.calculator.execute.space.ISpace;

public class ComplexMarkOpr extends AbstractOperator {

    private static final ImaginaryDealer dealer = new ImaginaryDealer();

    private static final ISpace<BaseNumber> DEFAULT_VALUE = new AtomSpace<>(MathUtil.multiplyI(BaseNumber.ONE));

    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        IExpression child = exp.nextChild();
        if(child == null){
            return DEFAULT_VALUE;
        }else{
            ISpace<BaseNumber> space = child.interpret();
            return OperateUtil.transform(space, dealer);
        }
    }

    public static class ImaginaryDealer implements IOneElementDealer{

        @Override
        public BaseNumber deal(BaseNumber num) {
            if(num != null){
                return MathUtil.multiplyI(num);
            }
            return null;
        }
    }

}
