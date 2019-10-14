package frog.calculator.math;

import frog.calculator.math.rational.RationalNumber;
import frog.calculator.math.real.PolynomialNumber;

public final class BaseNumber {

    private final static PolynomialNumber POLYNOMIAL_NUMBER_ZERO = new PolynomialNumber(RationalNumber.ZERO, null);

    private final static PolynomialNumber POLYNOMIAL_NUMBER_ONE = new PolynomialNumber(RationalNumber.ONE, null);

    private final static PolynomialNumber POLYNOMIAL_NUMBER_N_ONE = new PolynomialNumber(RationalNumber.N_ONE, null);

    public final static BaseNumber ZERO = new BaseNumber(POLYNOMIAL_NUMBER_ZERO);

    public final static BaseNumber ONE = new BaseNumber(POLYNOMIAL_NUMBER_ONE);

    private final PolynomialNumber polynomialNumber;

    public BaseNumber(PolynomialNumber polynomialNumber) {
        this.polynomialNumber = polynomialNumber;
    }

    public BaseNumber add(BaseNumber num) {
        return new BaseNumber(this.polynomialNumber.add(num.polynomialNumber));
    }

    public BaseNumber sub(BaseNumber num) {
        return new BaseNumber(this.polynomialNumber.subtract(num.polynomialNumber));
    }

    public BaseNumber mult(BaseNumber num) {
        return new BaseNumber(this.polynomialNumber.multiply(num.polynomialNumber));
    }

    public BaseNumber div(BaseNumber num) {
        return new BaseNumber(this.polynomialNumber.divide(num.polynomialNumber));
    }

    @Override
    public String toString() {
        return polynomialNumber.toString();
    }

    public String toDecimal(int precision){
        return polynomialNumber.toDecimal(precision);
    }
}
