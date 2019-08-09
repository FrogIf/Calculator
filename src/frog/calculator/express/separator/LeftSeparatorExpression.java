package frog.calculator.express.separator;

import frog.calculator.operator.IOperator;

/**
 * 做分隔符表达式<br/>
 * 分割符(即当前表达式)位于子节点的左边
 */
public class LeftSeparatorExpression extends SingleChildSeparatorExpression {

    public LeftSeparatorExpression(String symbol, int buildFactor, IOperator operator) {
        super(symbol, buildFactor, operator, true);
    }

    @Override
    protected boolean checkOrder(int selfOrder, int inputOrder) {
        return selfOrder < inputOrder;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }
}
