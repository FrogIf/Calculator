package frog.calculator.express.mid;

public class MultExpression extends MidExpression {

    private static final String SYMBOL = "*";

    private static final int priority = 2;

    @Override
    public int priority() {
        return priority;
    }

    @Override
    public String toString(){
        return SYMBOL;
    }

}
