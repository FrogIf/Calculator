package frog.calculator.execute.base;

import frog.calculator.execute.AbstractOperator;
import frog.calculator.execute.exception.IncorrectStructureException;
import frog.calculator.execute.space.ISpace;
import frog.calculator.express.IExpression;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;

public class RegionOpr extends AbstractOperator {
    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        IList<IExpression> argumentList = exp.children();

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
