package frog.calculator.express.separator;

import frog.calculator.express.AbstractExpression;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.operator.IOperator;

public class SeparatorExpression extends AbstractExpression {

    private IExpression right;

    private IExpression left;

    private int buildFactor;

    private boolean fifo = false;   // 指定相同buildFactor的表达式, 是作为当前表达式的根还是子节点

    public SeparatorExpression(String symbol, int buildFactor, IOperator operator, boolean fifo) {
        super(symbol, operator);
        this.buildFactor = buildFactor;
        this.fifo = fifo;
    }

    public SeparatorExpression(String symbol, int buildFactor, IOperator operator) {
        super(symbol, operator);
        this.buildFactor = buildFactor;
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
    public IExpression clone() {
        SeparatorExpression clone = (SeparatorExpression) super.clone();

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
    public IExpression assembleTree(IExpression expression) {
        IExpression root;
        boolean inputLeaf = expression.isLeaf();
        if(!inputLeaf && this.buildFactor() == expression.buildFactor()){   // 如果两个表达式的优先级相等
            if(fifo){
                root = this.createBranch(expression) ? this : null;
            }else{
                root = expression.createBranch(this) ? expression : null;
            }
        }else{
            IExpression low;
            IExpression high;

            // 这里没有判断this是否是leaf, 因为所有SeparatorExpression都不可能是leaf
            if(inputLeaf || this.buildFactor() < expression.buildFactor()){
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
