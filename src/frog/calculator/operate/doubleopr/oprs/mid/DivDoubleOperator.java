package frog.calculator.operate.doubleopr.oprs.mid;

public class DivDoubleOperator extends MidDoubleOperator {

    protected DivDoubleOperator(){}

    private static DivDoubleOperator instance = new DivDoubleOperator();

    public static DivDoubleOperator getInstance(){
        return instance;
    }

    @Override
    protected double calculate(double left, double right) {
        if(right == 0){
            throw new IllegalArgumentException("divisor can't be zero.");
        }
        return left / right;
    }
}
