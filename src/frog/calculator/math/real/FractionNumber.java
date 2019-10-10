package frog.calculator.math.real;

import frog.calculator.math.rational.RationalNumber;

// 分式
final class FractionNumber extends AbstractStructureNumber implements Comparable<FractionNumber> {

    /*
     * 整体结构是:
     *
     *              numerator * numeratorPolynomial
     *             ---------------------------------------
     *              denominator * denominatorPolynomial
     *
     */

    // 分子一定不为null
    private final FactorNumber numerator;

    private final PolynomialNumber numeratorPolynomial;

    private final FactorNumber denominator;

    private final PolynomialNumber denominatorPolynomial;

    FractionNumber(FactorNumber numerator, FactorNumber denominator, PolynomialNumber numeratorPolynomial, PolynomialNumber denominatorPolynomial) {
        this.numerator = numerator == null ? FactorNumber.ONE : numerator;
        this.numeratorPolynomial = numeratorPolynomial;
        this.denominator = denominator;
        this.denominatorPolynomial = denominatorPolynomial;
    }

    @Override
    public RationalNumber tryConvertToRational(){
        RationalNumber rationalA = this.numerator.tryConvertToRational();
        if(rationalA == null){ return null; }

        RationalNumber rationalC = null;
        if(this.denominator != null){
            rationalC = this.denominator.tryConvertToRational();
            if(rationalC == null) { return null; }
        }

        RationalNumber rationalB = null;
        if(this.numeratorPolynomial != null){
            rationalB = this.numeratorPolynomial.tryConvertToRational();
            if(rationalB == null){ return null; }
        }

        RationalNumber rationalD = null;
        if(this.denominatorPolynomial != null){
            rationalD = this.denominatorPolynomial.tryConvertToRational();
            if(rationalD == null) { return null; }
        }

        if(rationalB != null){
            rationalA = rationalA.mult(rationalB);
        }

        if(rationalC != null){
            rationalA = rationalA.div(rationalC);
        }

        if(rationalD != null){
            rationalA = rationalA.div(rationalD);
        }

        return rationalA;
    }

    FractionNumber tryAdd(FractionNumber num) {
//        boolean denominatorPolyEqual = (this.denominatorPolynomial == null && num.denominatorPolynomial == null) || (this.denominatorPolynomial != null && this.denominatorPolynomial.equals(num.denominatorPolynomial));
//        boolean denominatorEqual = (this.denominator == null && num.denominator == null) || (this.denominator != null && this.denominator.equals(num.denominator));
//
//        if(denominatorEqual && denominatorPolyEqual){   // 分母相等
//            if(this.numerator.equals(num.numerator)){
//                // 可以相加
//            }else if()
//        }else{
//
//        }
//
        return null;
    }

    FractionNumber mult(FractionNumber num) {
        FactorNumber resultNumerator = this.numerator.mult(num.numerator);

        PolynomialNumber resultNumeratorPolynomial = this.numeratorPolynomial;
        if(num.numeratorPolynomial != null){
            if(resultNumeratorPolynomial == null){
                resultNumeratorPolynomial = num.numeratorPolynomial;
            }else{
                resultNumeratorPolynomial = num.numeratorPolynomial.multiply(resultNumeratorPolynomial);
            }
        }

        FactorNumber resultDenominator = this.denominator;
        if(num.denominator != null){
            if(resultDenominator == null){
                resultDenominator = num.denominator;
            }else{
                resultDenominator = resultDenominator.mult(num.denominator);
            }
        }

        PolynomialNumber resultDenominatorPolynomial = this.denominatorPolynomial;
        if(num.denominatorPolynomial != null){
            if(resultDenominatorPolynomial == null){
                resultDenominatorPolynomial = num.denominatorPolynomial;
            }else{
                resultDenominatorPolynomial = resultNumeratorPolynomial.multiply(num.denominatorPolynomial);
            }
        }

        return new FractionNumber(resultNumerator, resultDenominator, resultNumeratorPolynomial, resultDenominatorPolynomial);
    }

    FractionNumber div(FractionNumber num) {
        FactorNumber resultNumerator;
        if(num.denominator == null){
            resultNumerator = this.numerator;
        }else{
            resultNumerator = this.numerator.mult(num.denominator);
        }

        PolynomialNumber resultNumeratorPolynomial = this.numeratorPolynomial;
        if(num.denominatorPolynomial != null){
            if(resultNumeratorPolynomial == null){
                resultNumeratorPolynomial = num.denominatorPolynomial;
            }else{
                resultNumeratorPolynomial = resultNumeratorPolynomial.multiply(num.denominatorPolynomial);
            }
        }

        FactorNumber resultDenominator;
        if(this.denominator == null){
            resultDenominator = num.numerator;
        }else{
            resultDenominator = num.numerator.mult(this.denominator);
        }

        PolynomialNumber resultDenominatorPolynomial = this.denominatorPolynomial;
        if(num.numeratorPolynomial != null){
            if(resultDenominatorPolynomial == null){
                resultDenominatorPolynomial = num.numeratorPolynomial;
            }else{
                resultDenominatorPolynomial = resultDenominatorPolynomial.multiply(num.numeratorPolynomial);
            }
        }

        return new FractionNumber(resultNumerator, resultDenominator, resultNumeratorPolynomial, resultDenominatorPolynomial);
    }

    @Override
    public FractionNumber not() {
        return new FractionNumber(this.numerator.not(), this.denominator, this.numeratorPolynomial, this.denominatorPolynomial);
    }

    FractionNumber mult(RationalNumber rational){
        return new FractionNumber(this.numerator.mult(rational), this.denominator, this.numeratorPolynomial, this.denominatorPolynomial);
    }

    FractionNumber div(RationalNumber rational){
        return new FractionNumber(this.numerator.div(rational), this.denominator, this.numeratorPolynomial, this.denominatorPolynomial);
    }

    @Override
    public String toDecimal(int count) {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.numerator.toString());
        if(this.numeratorPolynomial != null){
            sb.append('*');
            sb.append(this.numeratorPolynomial.toString());
        }
        if(this.denominator != null || this.denominatorPolynomial != null){
            sb.append('/');
        }
        if(this.denominator != null){
            sb.append(this.denominator.toString());
        }
        if(this.denominatorPolynomial != null){
            if(this.denominator != null){
                sb.append('*');
            }
            sb.append(this.denominatorPolynomial.toString());
        }
        return sb.toString();
    }

    @Override
    public int compareTo(FractionNumber o) {
        return 0;
    }
}
