package frog.calculator.dimpl.opr.two;

public class AddOperator extends LeftNullableOperator{

    @Override
    protected double doubleCalculate(double left, double right) {
        return left + right;
    }

}
