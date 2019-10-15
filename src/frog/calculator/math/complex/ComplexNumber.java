package frog.calculator.math.complex;

import frog.calculator.math.INumber;
import frog.calculator.math.rational.IntegerNumber;
import frog.calculator.math.real.PolynomialNumber;

/**
 * 复数
 */
public class ComplexNumber implements INumber, Comparable<ComplexNumber> {

    public static final ComplexNumber I = new ComplexNumber(null, PolynomialNumber.ONE);

    private final PolynomialNumber realPart;

    private final PolynomialNumber imaginaryPart;

    public ComplexNumber(PolynomialNumber realPart, PolynomialNumber imaginaryPart) {
        if(realPart == null && imaginaryPart == null){
            throw new IllegalArgumentException("can't init a empty object.");
        }
        this.realPart = realPart == null ? PolynomialNumber.ZERO : realPart;
        this.imaginaryPart = imaginaryPart == null ? PolynomialNumber.ZERO : imaginaryPart;
    }

    public ComplexNumber(PolynomialNumber realPart){
        if(realPart == null){
            throw new IllegalArgumentException("can't init a empty object.");
        }
        this.realPart = realPart;
        this.imaginaryPart = PolynomialNumber.ZERO;
    }

    public ComplexNumber add(ComplexNumber num){
        return new ComplexNumber(this.realPart.add(num.realPart), this.imaginaryPart.add(num.imaginaryPart));
    }

    public ComplexNumber sub(ComplexNumber num){
        return new ComplexNumber(this.realPart.subtract(num.realPart), this.imaginaryPart.subtract(num.imaginaryPart));
    }

    public ComplexNumber mult(ComplexNumber num){
        /*
         * (a + bi) * (c + di) = ac + bd + (ad + bc)i
         */
        return new ComplexNumber(this.realPart.multiply(num.realPart).subtract(this.imaginaryPart.multiply(num.imaginaryPart)),
                this.realPart.multiply(num.imaginaryPart).add(this.imaginaryPart.multiply(num.realPart)));
    }

    public ComplexNumber div(ComplexNumber num){
        /*
         * (a + bi)/(c + di) = (ac + bd)/(c*c + d*d) + ((bc - ad)/(c*c + d*d))i
         */
        PolynomialNumber cd = num.realPart.multiply(num.realPart).add(num.imaginaryPart.multiply(num.imaginaryPart));
        PolynomialNumber rp = this.realPart.multiply(num.realPart).add(this.imaginaryPart.multiply(num.imaginaryPart)).divide(cd);
        PolynomialNumber ip = this.imaginaryPart.multiply(num.realPart).subtract(this.realPart.multiply(num.imaginaryPart)).divide(cd);
        return new ComplexNumber(rp, ip);
    }

    @Override
    public ComplexNumber not() {
        return new ComplexNumber(realPart.not(), imaginaryPart.not());
    }

    @Override
    public String toDecimal(int count) {
        StringBuilder sb = new StringBuilder();
        if(!PolynomialNumber.ZERO.equals(this.realPart)){
            sb.append(this.realPart.toDecimal(count));
        }
        if(!PolynomialNumber.ZERO.equals(this.imaginaryPart)){
            if(sb.length() > 0){
                sb.append('+');
            }
            sb.append(this.imaginaryPart.toDecimal(count));
            sb.append('i');
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(!PolynomialNumber.ZERO.equals(this.realPart)){
            sb.append(this.realPart.toString());
        }
        if(!PolynomialNumber.ZERO.equals(this.imaginaryPart)){
            if(sb.length() > 0){
                sb.append('+');
            }
            sb.append(this.imaginaryPart.toString());
            sb.append('i');
        }
        return sb.toString();
    }

    /**
     * 该比较实际比较的是两个复数的模
     * @param o 比较的对象
     * @return 比较结果, 0 - 相等, 大于0则this大, 小于0则that大
     */
    @Override
    public int compareTo(ComplexNumber o) {
        return 0;
    }

    public IntegerNumber convertToInteger() {
        if(PolynomialNumber.ZERO.equals(this.imaginaryPart)){
            return this.realPart.convertToInteger();
        }
        return null;
    }
}
