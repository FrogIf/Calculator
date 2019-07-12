package frog.calculator.express;

import frog.calculator.operater.IOperator;

public abstract class ATerminalExpression extends APriorityExpression {

    public ATerminalExpression(IOperator operator, int priority, String symbol) {
        super(operator, priority, symbol);
    }

    @Override
    public final ExpressionType type() {
        return ExpressionType.TERMINAL;
    }

}
