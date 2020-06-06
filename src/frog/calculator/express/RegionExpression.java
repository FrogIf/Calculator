package frog.calculator.express;

import frog.calculator.execute.exception.IncorrectStructureException;
import frog.calculator.execute.space.ISpace;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.LinkedList;

/**
 * 域表达式, 即代码块
 */
public class RegionExpression extends SurroundExpression {

    public RegionExpression(String openSymbol, String separatorSymbol, String closeSymbol) {
        super(openSymbol, separatorSymbol, closeSymbol);
    }

    @Override
    public ISpace<BaseNumber> interpret() {
        IList<IExpression> argumentList = new LinkedList<>();
        while(this.hasNextChild()){
            argumentList.add(this.nextChild());
        }

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

    @Override
    public IExpression clone() {
        return super.clone();
    }
}
