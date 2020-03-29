package frog.calculator.express.template;

import frog.calculator.execute.IOperator;
import frog.calculator.express.IExpression;

public abstract class AbstractBlockExpression extends AbstractExpression {

    private final int buildFactor;

    public AbstractBlockExpression(String symbol, int buildFactor, IOperator operator){
        super(symbol, operator);
        this.buildFactor = buildFactor;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        IExpression root;
        boolean inputLeaf = expression.isLeaf();
        if(!inputLeaf && this.buildFactor() == expression.buildFactor()){   // 如果两个表达式的优先级相等
            root = expression.createBranch(this) ? expression : null;
        }else{
            IExpression low;
            IExpression high;

            // 这里没有判断this是否是leaf, 因为所有BlockExpression都不可能是leaf
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
    public int buildFactor() {
        return this.buildFactor;
    }

    @Override
    public IExpression clone() {
        return super.clone();
    }
}
