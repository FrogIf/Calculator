package frog.calculator.expressext;

import frog.calculator.operater.IOperator;

public class SeparatorTypeExpression extends AExpression {

    // 用于记录当前激活的子节点
    private IExpression activeChildExpression;

    private IExpression right;

    private IExpression left;

    private int priority;

    private IOperator operator;

    private IExpressionContext context;

    /**
     * 用于标记当前节点是否已关闭
     */
    private boolean isClose = false;

    public SeparatorTypeExpression(String symbol, int priority, IOperator operator) {
        super(symbol);
        this.operator = operator;
        this.priority = priority;
    }

    @Override
    public boolean createBranch(IExpression childExpression) {
        if(isClose) return false;
        return false;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        return null;
    }

    @Override
    public IExpression interpret() {
//        return this.operator.operate(this);
        return null;
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
        this.context = context;
    }

    public IExpression getRight() {
        return right;
    }

    public IExpression getLeft() {
        return left;
    }
}
