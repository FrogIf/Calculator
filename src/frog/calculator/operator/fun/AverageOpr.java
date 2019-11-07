package frog.calculator.operator.fun;

import frog.calculator.exception.IncorrectStructureException;
import frog.calculator.express.IExpression;
import frog.calculator.math.BaseNumber;
import frog.calculator.math.rational.IntegerNumber;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.operator.base.AddOpr;
import frog.calculator.operator.util.IOneElementDealer;
import frog.calculator.operator.util.OperateUtil;
import frog.calculator.space.ISpace;

public class AverageOpr extends AbstractOperator {

    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        IExpression child = exp.nextChild();
        if(child == null){
            throw new IncorrectStructureException("average", "no argument.");
        }else{
            if(!child.hasNextChild()){
                throw new IncorrectStructureException("average", "argument list is empty.");
            }
            IntegerNumber count = IntegerNumber.ONE;
            ISpace<BaseNumber> sumSpace = child.nextChild().interpret();
            while(child.hasNextChild()){
                count = count.increase();
                sumSpace = AddOpr.addSpace(child.nextChild().interpret(), sumSpace);
            }

            return OperateUtil.transform(sumSpace, new AverageDealer(BaseNumber.valueOf(count)));
        }
    }

    private static class AverageDealer implements IOneElementDealer {

        private final BaseNumber count;

        private AverageDealer(BaseNumber count) {
            this.count = count;
        }

        @Override
        public BaseNumber deal(BaseNumber num) {
            if(num != null){
                return num.div(count);
            }
            return null;
        }
    }
}
