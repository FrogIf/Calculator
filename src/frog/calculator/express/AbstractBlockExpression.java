package frog.calculator.express;

import frog.calculator.operator.IOperator;

public abstract class AbstractBlockExpression extends AbstractExpression {

//    private final boolean fifo;   // 指定相同buildFactor的表达式, 是作为当前表达式的根还是子节点

    private final int buildFactor;

    public AbstractBlockExpression(String symbol, int buildFactor, IOperator operator){
        super(symbol, operator);
        this.buildFactor = buildFactor;
//        this.fifo = fifo;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        IExpression root;
        boolean inputLeaf = expression.isLeaf();
        if(!inputLeaf && this.buildFactor() == expression.buildFactor()){   // 如果两个表达式的优先级相等
//            if(fifo){
                root = this.createBranch(expression) ? this : null;
//            }else{
//                root = expression.createBranch(this) ? expression : null;
//            }
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
    public int buildFactor() {
        return this.buildFactor;
    }

}
