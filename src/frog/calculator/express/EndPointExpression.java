package frog.calculator.express;

import frog.calculator.execute.IOperator;
import frog.calculator.execute.space.ISpace;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.util.collection.IList;

public class EndPointExpression extends AbstractExpression {

    public EndPointExpression(String symbol, IOperator operator) {
        super(symbol, operator);
        this.operator = operator;
    }

    @Override
    public boolean createBranch(IExpression childExpression) {
        // endpoint can't create branch.
        return false;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        if(expression.isLeaf()){
            return null;
        }else{
            return expression.assembleTree(this);
        }
    }

    @Override
    public ISpace<BaseNumber> interpret() {
        return operator.operate(this);
    }

    @Override
    public IList<IExpression> children() {
        return null;
    }

    @Override
    public final boolean isLeaf() {
        return true;
    }

    @Override
    public int buildFactor() {
        // because of it is leaf, so this is not important.
        return 1;
    }

    @Override
    public IExpression newInstance() {
        EndPointExpression endPointExpression = new EndPointExpression(this.symbol, this.operator);
        this.copyProperty(endPointExpression);
        return endPointExpression;
    }

}
