package frog.calculator.dimpl.opr.two;

public class SubOperator extends LeftNullableOperator {
    @Override
    protected double doubleCalculate(double left, double right) {
        return left - right;
    }
}
