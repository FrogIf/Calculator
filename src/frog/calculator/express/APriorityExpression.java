package frog.calculator.express;

import frog.calculator.express.result.AResultExpression;
import frog.calculator.operater.IOperator;

public abstract class APriorityExpression implements IExpression {

    private IOperator operator;

    private int priority;

    private String symbol;

    public APriorityExpression(IOperator operator, int priority, String symbol) {
        if(operator == null){
            throw new IllegalArgumentException("operator cat't null.");
        }
        this.operator = operator;
        this.priority = priority;
        this.symbol = symbol;
    }

    @Override
    public String symbol() {
        return this.symbol;
    }

    @Override
    public int priority() {
        return priority;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        IExpression root;
        if(this.priority() == expression.priority()){
            // 如果两个表达式的优先级相等

            // 尝试将传入表达式作为根
            if(expression.createBranch(this)){
                root = expression;
            }else{
                // 尝试使用当前表达式作为根
                if(this.createBranch(expression)){
                    root = this;
                }else{
                    root = null;
                }
            }
        }else{
            IExpression low;
            IExpression high;
            if(this.priority() < expression.priority()){
                low = this;
                high = expression;
            }else{
                low = expression;
                high = this;
            }

            // 使用低优先级作为解析树作为根
            root = low.createBranch(high) ? low : null;
        }

        return root;
    }

    @Override
    public AResultExpression interpret() {
        if(this.getOperator() == null){
            throw new IllegalArgumentException("can't find operator.");
        }
        return this.getOperator().operate(this);
    }

    @Override
    public IExpression clone(){
        try {
            APriorityExpression cExp = (APriorityExpression) super.clone();
            return cExp;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public IOperator getOperator(){
        return this.operator;
    }

    @Override
    public String toString(){
        return this.symbol();
    }

    private void setOperator(IOperator operator){
        this.operator = operator;
    }
}
