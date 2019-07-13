package frog.calculator.dimpl.opr.single;

public class FactorialOperator extends SingleArgOperator {
    @Override
    protected double doubleCalcuate(double arg) {
        return factorial(arg);
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
