package frog.calculator.operater.oprimpl.dimpl.opr.two;

public class SubOperator extends TwoArgOperator {
    @Override
    protected double doubleCalculate(double left, double right) {
        // TODO 减号需要考虑左侧为null的情况
        return left - right;
    }
}
