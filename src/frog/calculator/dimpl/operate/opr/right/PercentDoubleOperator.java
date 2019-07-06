package frog.calculator.dimpl.operate.opr.right;

public class PercentDoubleOperator extends RightDoubleOperator {

    @Override
    protected double calculate(double left) {
        return left / 100;
    }

}
