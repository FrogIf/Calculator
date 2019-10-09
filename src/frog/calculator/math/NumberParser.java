package frog.calculator.math;

import frog.calculator.math.rational.RationalNumber;
import frog.calculator.math.real.PolynomialNumber;

public class NumberParser {

    public static BaseNumber parseNumber(String symbol){
        RationalNumber rationalNumber = new RationalNumber(symbol);
        return new BaseNumber(new PolynomialNumber(rationalNumber, null));
    }

}
