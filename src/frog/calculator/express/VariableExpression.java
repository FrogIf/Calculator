package frog.calculator.express;

import frog.calculator.operator.IOperator;

public class VariableExpression extends AbstractExpression {

    /*
     * 第一个子是结构符号:等于, 括号, 逗号等, 则变身左表达式
     *          将右边的全都放在它的子树中
     * 如果第一个子不是等于号, 则为普通变量
     */

    public VariableExpression(String symbol, IOperator operator) {
        super(symbol, operator);
    }

    @Override
    public boolean createBranch(IExpression childExpression) {
        return false;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        return null;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public int buildFactor() {
        return 0;
    }

    @Override
    public void setExpressionContext(IExpressionContext context) {

    }

    @Override
    public boolean hasNextChild() {
        return false;
    }

    @Override
    public IExpression nextChild() {
        return null;
    }
}
