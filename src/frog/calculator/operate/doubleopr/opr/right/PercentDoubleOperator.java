package frog.calculator.operate.doubleopr.opr.right;

public class PercentDoubleOperator extends RightDoubleOperator {

    @Override
    protected double calculate(double left) {
        return left / 100;
    }

}
