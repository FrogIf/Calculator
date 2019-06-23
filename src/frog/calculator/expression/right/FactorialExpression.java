package frog.calculator.expression.right;

import frog.calculator.expression.IExpression;

public class FactorialExpression extends RightExpression {

    private IExpression left;

    private static final int priority = 3;

    @Override
    public double interpret() {
        double value = left.interpret();
        return factorial(value);
    }

    private double factorial(double value){
        if(!isInteger(value)){
            throw new IllegalArgumentException("factorial can't apply to decimal.");
        }

        int a = (int) value;
        int result = 1;
        for(; a > 0; a--){
            result *= a;
        }

        return result;
    }

    private boolean isInteger(double value){
        return value == (int)value;
    }

    @Override
    public IExpression getLeft() {
        return null;
    }

    @Override
    public void setLeft(IExpression left) {
        this.left = left;
    }

    @Override
    public int getPriority() {
        return priority;
    }

}
