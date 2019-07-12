package frog.calculator.express;

import frog.calculator.operater.IOperator;

public abstract class ACombinedExpression extends APriorityExpression {

    public ACombinedExpression(IOperator operator, int priority, String symbol) {
        super(operator, priority, symbol);
    }

    @Override
    public final ExpressionType type() {
        return ExpressionType.COMBINED;
    }
}
