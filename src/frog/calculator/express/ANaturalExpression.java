package frog.calculator.express;

import frog.calculator.operater.IOperator;

public abstract class ANaturalExpression extends APriorityExpression {

    public ANaturalExpression(IOperator operator, int priority, String symbol) {
        super(operator, priority, symbol);
    }

    @Override
    public final ExpressionType type() {
        return ExpressionType.NATURAL;
    }
}
