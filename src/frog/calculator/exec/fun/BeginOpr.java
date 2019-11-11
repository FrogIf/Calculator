package frog.calculator.exec.fun;

import frog.calculator.exec.exception.IncorrectStructureException;
import frog.calculator.express.IExpression;
import frog.calculator.math.BaseNumber;
import frog.calculator.exec.AbstractOperator;
import frog.calculator.exec.util.OperateUtil;
import frog.calculator.exec.space.ISpace;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;

public class BeginOpr extends AbstractOperator {

    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        IList<IExpression> argumentList = OperateUtil.getFunctionArgumentList(exp);
        if(argumentList.isEmpty()){
            throw new IncorrectStructureException("average", "argument list is empty.");
        }

        Iterator<IExpression> iterator = argumentList.iterator();
        ISpace<BaseNumber> result = null;
        while(iterator.hasNext()){
            result = iterator.next().interpret();
        }
        return result;
    }

}
