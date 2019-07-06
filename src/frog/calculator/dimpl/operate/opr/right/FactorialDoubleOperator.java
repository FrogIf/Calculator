package frog.calculator.dimpl.operate.opr.right;

public class FactorialDoubleOperator extends RightDoubleOperator{

    @Override
    protected double calculate(double left) {
        return factorial(left);
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

}
