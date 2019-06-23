package frog.calculator.express;

import frog.calculator.express.result.ResultExpression;
import frog.calculator.operate.IOperator;

public abstract class PriorityExpression implements IExpression {

    private IOperator operator;

    @Override
    public void setOperator(IOperator operator){
        this.operator = operator;
    }

    @Override
    public boolean isLeaf(){
        return false;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        IExpression root;
        if(this.getPriority() == expression.getPriority()){
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
            if(this.getPriority() < expression.getPriority()){
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
    public ResultExpression interpret() {
        return operator.operate(this);
    }
}
