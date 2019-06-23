package frog.calculator.operate.doubleopr.oprs.mid;

import frog.calculator.express.mid.AddExpression;

public class AddDoubleOperator extends MidDoubleOperator<AddExpression> {

    protected AddDoubleOperator(){}

    private static AddDoubleOperator instance = new AddDoubleOperator();

    public static AddDoubleOperator getInstance(){
        return instance;
    }

    @Override
    protected double calculate(double left, double right) {
        return left + right;
    }
}
