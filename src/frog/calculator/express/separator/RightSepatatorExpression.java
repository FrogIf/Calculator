package frog.calculator.express.separator;

import frog.calculator.operater.IOperator;

public class RightSepatatorExpression extends SingleChildSepatatorExpression {

    public RightSepatatorExpression(String symbol, int buildFactor, IOperator operator) {
        super(symbol, buildFactor, operator);
    }

    @Override
    protected boolean checkOrder(int selfOrder, int inputOrder) {
        return selfOrder > inputOrder;
    }
}
