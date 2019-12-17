package frog.calculator.express;

import frog.calculator.exec.IOperator;

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
    public boolean hasNextChild() {
        return false;
    }

    @Override
    public IExpression nextChild() {
        return this.left;
    }

    @Override
    public IExpression clone() {
        RightExpression expression = (RightExpression) super.clone();
        if(this.left != null){
            expression.left = this.left.clone();
        }
        return expression;
    }
}
