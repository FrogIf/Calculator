package frog.calculator.operater.oprimpl.dimpl.opr.two;

public class DivOperator extends TwoArgOperator {
    @Override
    protected double doubleCalculate(double left, double right) {
        return left / right;
    }
}
