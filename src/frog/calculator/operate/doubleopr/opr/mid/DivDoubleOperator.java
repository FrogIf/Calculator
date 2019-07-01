package frog.calculator.operate.doubleopr.opr.mid;

public class DivDoubleOperator extends MidDoubleOperator {

    @Override
    protected double calculate(double left, double right) {
        if(right == 0){
            throw new IllegalArgumentException("divisor can't be zero.");
        }
        return left / right;
    }
}
