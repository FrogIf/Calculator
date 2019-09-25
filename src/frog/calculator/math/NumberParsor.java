package frog.calculator.math;

public class NumberParsor {

    public static INumber parseNumber(String symbol){
        return new RationalNumber(symbol);
    }

}
