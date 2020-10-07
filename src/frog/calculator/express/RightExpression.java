package frog.calculator.express;

import frog.calculator.execute.IOperator;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;

public class RightExpression extends AbstractUndetachableExpression {

    private IExpression left;

    public RightExpression(String symbol, int buildFactor, IOperator operator){
        super(symbol, buildFactor, operator);
    }

    @Override
    public boolean createBranch(IExpression childExpression) {
        if(this.order > childExpression.order() && this.left == null) {
            this.left = childExpression;
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public IExpression newInstance() {
        RightExpression rightExpression = new RightExpression(this.symbol, this.buildFactor(), this.operator);
        this.copyProperty(rightExpression);
        if (this.left != null) {
            rightExpression.left = this.left.newInstance();
        }
        return rightExpression;
    }

    @Override
    public IList<IExpression> children() {
        return new ArrayList<>(new IExpression[]{this.left});
    }

}
