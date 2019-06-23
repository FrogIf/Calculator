package frog.calculator.express.mid;

public class SubExpression extends MidExpression {
    
    private static final String SYMBOL = "-";

    private static final int priority = 1;

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return SYMBOL;
    }
}
