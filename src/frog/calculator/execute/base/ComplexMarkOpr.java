package frog.calculator.execute.base;

import frog.calculator.execute.AbstractSingleArgOpr;
import frog.calculator.execute.space.AtomSpace;
import frog.calculator.execute.space.ISpace;
import frog.calculator.execute.util.IOneElementDealer;
import frog.calculator.execute.util.OperateUtil;
import frog.calculator.express.IExpression;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.math.tool.MathUtil;

public class ComplexMarkOpr extends AbstractSingleArgOpr {

    private static final ImaginaryDealer dealer = new ImaginaryDealer();

    private static final ISpace<BaseNumber> DEFAULT_VALUE = new AtomSpace<>(MathUtil.multiplyI(BaseNumber.ONE));

    @Override
    protected ISpace<BaseNumber> exec(IExpression child) {
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
