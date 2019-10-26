package frog.calculator.math;

import frog.calculator.math.complex.ComplexNumber;
import frog.calculator.math.rational.IntegerNumber;
import frog.calculator.math.rational.RationalNumber;
import frog.calculator.math.real.PolynomialNumber;

public final class BaseNumber {

    private final static ComplexNumber COMPLEX_ZERO = new ComplexNumber(new PolynomialNumber(RationalNumber.ZERO, null));

    private final static ComplexNumber COMPLEX_ONE = new ComplexNumber(new PolynomialNumber(RationalNumber.ONE, null));

    public final static BaseNumber ZERO = new BaseNumber(COMPLEX_ZERO);

    public final static BaseNumber ONE = new BaseNumber(COMPLEX_ONE);

    private final ComplexNumber complex;

    BaseNumber(ComplexNumber complex) {
        this.complex = complex;
    }

    public BaseNumber add(BaseNumber num) {
        return new BaseNumber(this.complex.add(num.complex));
    }

    public BaseNumber sub(BaseNumber num) {
        return new BaseNumber(this.complex.sub(num.complex));
    }

    public BaseNumber mult(BaseNumber num) {
        return new BaseNumber(this.complex.mult(num.complex));
    }

    public BaseNumber div(BaseNumber num) {
        return new BaseNumber(this.complex.div(num.complex));
    }

    @Override
    public String toString() {
        return complex.toString();
    }

    public String toDecimal(int precision){
        return this.complex.toDecimal(precision);
    }

    public RationalNumber tryToConvertToRational(){
        return complex.tryConvertToRational();
    }

    public static BaseNumber valueOf(int num){
        return new BaseNumber(new ComplexNumber(new PolynomialNumber(new RationalNumber(IntegerNumber.valueOf(num), null), null)));
    }

    public static BaseNumber valueOf(String decimal){
        return new BaseNumber(new ComplexNumber(new PolynomialNumber(new RationalNumber(decimal), null)));
    }

    public static BaseNumber valueOf(IntegerNumber num){
        return new BaseNumber(new ComplexNumber(new PolynomialNumber(new RationalNumber(num, null), null)));
    }

    public static BaseNumber valueOf(RationalNumber num){
        return new BaseNumber(new ComplexNumber(new PolynomialNumber(num, null)));
    }
}
