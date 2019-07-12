package frog.calculator.expressext.separator;

import frog.calculator.expressext.AExpression;
import frog.calculator.expressext.IExpression;
import frog.calculator.expressext.IExpressionContext;

public class SeparatorExpression extends AExpression {

    private IExpression right;

    private IExpression left;

    private int priority;

    private IOperator operator;

    /**
     * 用于标记当前节点是否已关闭
     */
    private boolean isClose = false;

    public SeparatorExpression(String symbol, int priority, IOperator operator) {
        super(symbol);
        this.operator = operator;
        this.priority = priority;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        IExpression root;
        if(this.buildFactor() == expression.buildFactor()){   // 如果两个表达式的优先级相等
            if(expression.createBranch(this)){  // 尝试将传入表达式作为根
                root = expression;
            }else{  // 尝试使用当前表达式作为根
                root = this.createBranch(expression) ? this : null;
            }
        }else{
            IExpression low;
            IExpression high;
            if(this.buildFactor() < expression.buildFactor()){
                low = this;
                high = expression;
            }else{
                low = expression;
                high = this;
            }

            // 使用低优先级作为解析树的根
            root = low.createBranch(high) ? low : null;
        }
        return root;
    }

    @Override
    public IExpression interpret() {
        return this.operator.operate(this);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public int buildFactor() {
        return this.priority;
    }

    @Override
    public void setExpressionContext(IExpressionContext context) {
        // do nothing.
    }

    public IExpression getRight() {
        return right;
    }

    public IExpression getLeft() {
        return left;
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
        return this.left == null ? expression : this.left.assembleTree(expression);
    }

    protected  IExpression assembleRightTree(IExpression expression){
        return this.right == null ? expression : this.right.assembleTree(expression);
    }


}
