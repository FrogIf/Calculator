package frog.calculator.math;

public class NumberParser {

    public static INumber parseNumber(String symbol){
        return new RationalNumber(symbol);
    }

}
