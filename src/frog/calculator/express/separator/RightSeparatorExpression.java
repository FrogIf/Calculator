package frog.calculator.express.separator;

import frog.calculator.operator.IOperator;

public class RightSeparatorExpression extends SingleChildSeparatorExpression {

    public RightSeparatorExpression(String symbol, int buildFactor, IOperator operator) {
        super(symbol, buildFactor, operator, false);
    }

    @Override
    protected boolean checkOrder(int selfOrder, int inputOrder) {
        return selfOrder > inputOrder;
    }
}
