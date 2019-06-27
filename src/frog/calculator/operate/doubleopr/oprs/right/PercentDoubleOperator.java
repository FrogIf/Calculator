package frog.calculator.operate.doubleopr.oprs.right;

public class PercentDoubleOperator extends RightDoubleOperator {

    private static PercentDoubleOperator instance = new PercentDoubleOperator();

    protected PercentDoubleOperator() {}

    public static PercentDoubleOperator getInstance(){
        return instance;
    }

    @Override
    protected double calculate(double left) {
        return left / 100;
    }
}
