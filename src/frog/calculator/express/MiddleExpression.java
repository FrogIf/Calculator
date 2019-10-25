package frog.calculator.express;

import frog.calculator.operator.IOperator;

public class MiddleExpression extends AbstractBlockExpression{

    private IExpression right;

    private IExpression left;

//    public MiddleExpression(String symbol, int buildFactor, IOperator operator, boolean fifo) {
//        super(symbol, buildFactor, operator, fifo);
//    }

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
    public void setExpressionContext(IExpressionContext context) {
        if(this.left != null){
            this.left.setExpressionContext(context);
        }
        if(this.right != null){
            this.right.setExpressionContext(context);
        }
        this.context = context;
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

    public IExpression getRight() {
        return right;
    }

    public IExpression getLeft() {
        return left;
    }

    private int mi = 0;

    @Override
    public boolean hasNextChild() {
        return mi < 2;
    }

    @Override
    public IExpression nextChild() {
        if(mi == 0){
            mi++;
            return this.left;
        }else if(mi == 1){
            mi++;
            return this.right;
        }else{
            return null;
        }
    }
}
