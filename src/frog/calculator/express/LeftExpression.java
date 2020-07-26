package frog.calculator.express;

import frog.calculator.execute.IOperator;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;

public class LeftExpression extends AbstractBlockExpression {

    private IExpression right;

    public LeftExpression(String symbol, int buildFactor, IOperator operator){
        super(symbol, buildFactor, operator);
    }

    @Override
    public boolean createBranch(IExpression childExpression) {
        if(this.order < childExpression.order() && this.right == null){
            this.right = childExpression;
            return true;
        }
        return false;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public IExpression newInstance() {
        LeftExpression leftExpression = new LeftExpression(this.symbol, this.buildFactor(), this.operator);
        this.copyProperty(leftExpression);
        if(this.right != null){
            leftExpression.right = this.right.newInstance();
        }
        return leftExpression;
    }

    @Override
    public IList<IExpression> children() {
        return new ArrayList<>(new IExpression[]{this.right});
    }

}
