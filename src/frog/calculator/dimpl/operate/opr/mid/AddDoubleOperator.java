package frog.calculator.dimpl.operate.opr.mid;

public class AddDoubleOperator extends MidDoubleOperator{

    @Override
    protected double calculate(double left, double right) {
        return left + right;
    }
}
