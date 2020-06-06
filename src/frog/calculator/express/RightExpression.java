package frog.calculator.express;

import frog.calculator.execute.IOperator;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.ITraveller;
import frog.calculator.util.collection.SingleElementTraveller;

public class RightExpression extends AbstractBlockExpression {

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
    public IExpression clone() {
        RightExpression expression = (RightExpression) super.clone();
        if(this.left != null){
            expression.left = this.left.clone();
        }
        return expression;
    }

    @Override
    public IList<IExpression> children() {
        return new ArrayList<>(new IExpression[]{this.left});
    }

}
