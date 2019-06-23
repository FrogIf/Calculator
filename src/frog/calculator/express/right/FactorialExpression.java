package frog.calculator.express.right;

public class FactorialExpression extends RightExpression {

    private static final int priority = 3;

    @Override
    public int getPriority() {
        return priority;
    }

}
