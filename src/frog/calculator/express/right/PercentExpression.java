package frog.calculator.express.right;

public class PercentExpression extends RightExpression {

    private static final int priority = 3;

    @Override
    public int priority() {
        return priority;
    }
}
