package frog.calculator.execute.fun;

import frog.calculator.execute.AbstractSingleArgOpr;
import frog.calculator.execute.base.AddOpr;
import frog.calculator.execute.exception.IncorrectStructureException;
import frog.calculator.execute.space.ISpace;
import frog.calculator.express.IExpression;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;

public class SumOpr extends AbstractSingleArgOpr {

    @Override
    protected ISpace<BaseNumber> exec(IExpression child) {
        if(child == null){
            throw new IncorrectStructureException("sum", "child is null.");
        }else{
            IList<IExpression> args = child.children();
            if(args.size() == 0){
                throw new IncorrectStructureException("sum", "empty argument.");
            }
            Iterator<IExpression> iterator = args.iterator();
            ISpace<BaseNumber> sumSpace = iterator.next().interpret();
            while(iterator.hasNext()){
                sumSpace = AddOpr.addSpace(iterator.next().interpret(), sumSpace);
            }

            return sumSpace;
        }
    }

}
