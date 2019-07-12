package frog.calculator.operater.oprimpl.dimpl.opr.mid;

public class AddDoubleOperator extends MidDoubleOperator{

    @Override
    protected double calculate(double left, double right) {
        return left + right;
    }
}
