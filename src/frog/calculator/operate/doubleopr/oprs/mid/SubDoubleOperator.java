package frog.calculator.operate.doubleopr.oprs.mid;

public class SubDoubleOperator extends MidDoubleOperator {

    protected SubDoubleOperator(){}

    private static SubDoubleOperator instance = new SubDoubleOperator();

    public static SubDoubleOperator getInstance(){
        return instance;
    }

    @Override
    protected double calculate(double left, double right) {
        return left - right;
    }
}
