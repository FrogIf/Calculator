package frog.calculator.operate.doubleopr.oprs.mid;

public class MultDoubleOperator extends MidDoubleOperator {

    protected MultDoubleOperator(){}

    private static MultDoubleOperator instance = new MultDoubleOperator();

    public static MultDoubleOperator getInstance(){
        return instance;
    }

    @Override
    protected double calculate(double left, double right) {
        return left * right;
    }

}
