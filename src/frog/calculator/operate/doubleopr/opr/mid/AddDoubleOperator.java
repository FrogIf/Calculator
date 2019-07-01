package frog.calculator.operate.doubleopr.opr.mid;

import frog.calculator.express.mid.AddExpression;

public class AddDoubleOperator extends MidDoubleOperator<AddExpression> {

    @Override
    protected double calculate(double left, double right) {
        return left + right;
    }
}
