package frog.calculator.express.separator;

import frog.calculator.operater.IOperator;

/**
 * 做分隔符表达式<br/>
 * 分割符(即当前表达式)位于子节点的左边
 */
public class LeftSepatatorExpression extends SingleChildSepatatorExpression {

    public LeftSepatatorExpression(String symbol, int buildFactor, IOperator operator) {
        super(symbol, buildFactor, operator);
    }

    @Override
    protected boolean checkOrder(int selfOrder, int inputOrder) {
        return selfOrder < inputOrder;
    }
}
