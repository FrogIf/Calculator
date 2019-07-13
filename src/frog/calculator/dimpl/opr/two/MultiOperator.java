package frog.calculator.dimpl.opr.two;

public class MultiOperator extends TwoArgOperator {
    @Override
    protected double doubleCalculate(double left, double right) {
        return left * right;
    }
}
