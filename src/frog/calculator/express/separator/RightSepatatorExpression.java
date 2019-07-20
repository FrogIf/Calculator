package frog.calculator.express.separator;

import frog.calculator.operator.IOperator;

public class RightSepatatorExpression extends SingleChildSeparatorExpression {

    public RightSepatatorExpression(String symbol, int buildFactor, IOperator operator) {
        super(symbol, buildFactor, operator);
    }

    @Override
    protected boolean checkOrder(int selfOrder, int inputOrder) {
        return selfOrder > inputOrder;
    }
}
