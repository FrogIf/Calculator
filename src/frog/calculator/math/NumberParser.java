package frog.calculator.math;

import frog.calculator.math.complex.ComplexNumber;
import frog.calculator.math.rational.IntegerNumber;
import frog.calculator.math.rational.RationalNumber;
import frog.calculator.math.real.PolynomialNumber;
import frog.calculator.util.StringUtils;

public class NumberParser {

    public static BaseNumber parseNumber(String symbol){
        if(StringUtils.isBlank(symbol)){
            throw new IllegalArgumentException("can't parse this number.");
        }

        int dot1 = symbol.indexOf('.');
        int dot2 = symbol.lastIndexOf('.');
        if(dot1 == dot2 || dot2 == symbol.length() - 1){
            return BaseNumber.valueOf(symbol);
        }else{
            String num = symbol.substring(0, dot2);
            String repetend = symbol.substring(dot2 + 1);
            IntegerNumber repetendNum = IntegerNumber.valueOf(repetend);
            RationalNumber resultRational = new RationalNumber(num, 2);
            return new BaseNumber(new ComplexNumber(new PolynomialNumber(resultRational, null)));
        }
    }

}
