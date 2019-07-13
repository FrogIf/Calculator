package frog.calculator.express;

import frog.calculator.operater.IOperator;

public abstract class AExpression implements IExpression{

    private String symbol;

    protected int order = -1;

    protected IOperator operator;

    public AExpression(String symbol, IOperator operator) {
        this.symbol = symbol;
        this.operator = operator;
    }

    @Override
    public String symbol() {
        return this.symbol;
    }

    @Override
    public IExpression clone() {
        try {
            return (IExpression) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public IOperator getOperator() {
        return operator;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        IExpression root;
        boolean inputLeaf = expression.isLeaf();
        if(!inputLeaf && this.buildFactor() == expression.buildFactor()){   // 如果两个表达式的优先级相等
            if(expression.createBranch(this)){  // 尝试将传入表达式作为根
                root = expression;
            }else{  // 尝试使用当前表达式作为根
                root = this.createBranch(expression) ? this : null;
            }
        }else{
            IExpression low;
            IExpression high;

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
    public String toString() {
        return this.symbol;
    }

    @Override
    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int order() {
        return this.order;
    }
}
