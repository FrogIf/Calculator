package frog.calculator.express;

import frog.calculator.execute.IOperator;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;

public class MiddleExpression extends AbstractBlockExpression{

    private IExpression right;

    private IExpression left;

    public MiddleExpression(String symbol, int buildFactor, IOperator operator) {
        super(symbol, buildFactor, operator);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public IExpression clone() {
        MiddleExpression clone = (MiddleExpression) super.clone();
        clone.left = this.left == null ? null : this.left.clone();
        clone.right = this.right == null ? null : this.right.clone();
        return clone;
    }

    @Override
    public IList<IExpression> children() {
        return new ArrayList<>(new IExpression[]{this.left, this.right});
    }

    @Override
    public boolean createBranch(IExpression childExpression) {
        boolean buildSuccess = false;

        IExpression lRoot = this.assembleLeftTree(childExpression);
        if(lRoot != null){
            this.left = lRoot;
            buildSuccess = true;
        }

        if(!buildSuccess){
            IExpression rRoot = this.assembleRightTree(childExpression);
            if(rRoot != null){
                this.right = rRoot;
                buildSuccess = true;
            }
        }

        return buildSuccess;
    }

    private IExpression assembleLeftTree(IExpression expression){
        if(this.order < expression.order()){
            return null;
        }
        return this.left == null ? expression : this.left.assembleTree(expression);
    }

    private IExpression assembleRightTree(IExpression expression){
        if(this.order > expression.order()){
            return null;
        }
        return this.right == null ? expression : this.right.assembleTree(expression);
    }

}
