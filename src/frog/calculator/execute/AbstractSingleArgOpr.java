package frog.calculator.execute;

import frog.calculator.execute.space.ISpace;
import frog.calculator.express.IExpression;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.util.collection.IList;

public abstract class AbstractSingleArgOpr extends AbstractOperator {

    protected abstract ISpace<BaseNumber> eval(IExpression child);

    @Override
    public ISpace<BaseNumber> evaluate(IExpression exp) {
        IList<IExpression> children = exp.children();
        return this.eval(children.size() == 0 ? null : children.get(0));
    }
}
