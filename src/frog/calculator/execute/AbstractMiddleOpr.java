package frog.calculator.execute;

import frog.calculator.execute.space.ISpace;
import frog.calculator.express.IExpression;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.ITraveller;

public abstract class AbstractMiddleOpr extends AbstractOperator {

    protected abstract ISpace<BaseNumber> exec(IExpression left, IExpression right);

    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        IList<IExpression> children = exp.children();
        ITraveller<? extends IExpression> traveler = children.iterator();
        IExpression left = traveler.next();
        IExpression right = traveler.next();
        return this.exec(left, right);
    }
}
