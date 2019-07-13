package frog.calculator.express.separator;

import frog.calculator.express.AExpression;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.operater.IOperator;

public class SeparatorExpression extends AExpression {

    private IExpression right;

    private IExpression left;

    private int buildFactor;

    /**
     * 用于标记当前节点是否已关闭
     */
    protected boolean isClose = false;

    public SeparatorExpression(String symbol, int buildFactor, IOperator operator) {
        super(symbol, operator);
        this.buildFactor = buildFactor;
    }

    @Override
    public IExpression interpret() {
        return this.operator.operate(this.symbol(), this.left, this.right);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public int buildFactor() {
        return this.buildFactor;
    }

    @Override
    public void setExpressionContext(IExpressionContext context) {
        // do nothing.
    }

    @Override
    public IExpression clone() {
        SeparatorExpression clone = (SeparatorExpression) super.clone();

        clone.left = this.left == null ? null : this.left.clone();
        clone.right = this.right == null ? null : this.right.clone();

        return clone;
    }

    @Override
    public boolean createBranch(IExpression childExpression) {
        if(isClose) return false;

        boolean buildSuccess = false;
        if(this.right == null){
            IExpression lRoot = this.assembleLeftTree(childExpression);
            if(lRoot != null){
                this.left = lRoot;
                buildSuccess = true;
            }
        }

        if(!buildSuccess){
            IExpression rRoot = this.assembleRightTree(childExpression);
            if(rRoot != null){
                this.right = rRoot;
                buildSuccess = true;
            }
        }

        isClose = !buildSuccess;

        return buildSuccess;
    }

    protected IExpression assembleLeftTree(IExpression expression){
        if(this.order < expression.order()){
            return null;
        }
        return this.left == null ? expression : this.left.assembleTree(expression);
    }

    protected IExpression assembleRightTree(IExpression expression){
        if(this.order > expression.order()){
            return null;
        }
        return this.right == null ? expression : this.right.assembleTree(expression);
    }


}
