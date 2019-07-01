package frog.calculator.operate.doubleopr.opr.mid;

public class SubDoubleOperator extends MidDoubleOperator {

    @Override
    protected double calculate(double left, double right) {
        return left - right;
    }
}
