package frog.calculator.operator.fun;

import frog.calculator.operator.exception.IncorrectStructureException;
import frog.calculator.express.IExpression;
import frog.calculator.math.BaseNumber;
import frog.calculator.math.rational.IntegerNumber;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.operator.base.AddOpr;
import frog.calculator.operator.util.IOneElementDealer;
import frog.calculator.operator.util.OperateUtil;
import frog.calculator.space.ISpace;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;

public class AverageOpr extends AbstractOperator {

    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        IList<IExpression> argumentList = OperateUtil.getFunctionArgumentList(exp);
        if(argumentList.isEmpty()){
            throw new IncorrectStructureException("average", "argument list is empty.");
        }
        IntegerNumber count = IntegerNumber.valueOf(argumentList.size());
        Iterator<IExpression> iterator = argumentList.iterator();
        ISpace<BaseNumber> sumSpace = iterator.next().interpret();
        while(iterator.hasNext()){
            sumSpace = AddOpr.addSpace(iterator.next().interpret(), sumSpace);
        }

        return OperateUtil.transform(sumSpace, new AverageDealer(BaseNumber.valueOf(count)));
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
