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
        boolean denominatorPolyEqual = (this.denominatorPolynomial == null && num.denominatorPolynomial == null) || (this.denominatorPolynomial != null && this.denominatorPolynomial.equals(num.denominatorPolynomial));
        boolean denominatorEqual = (this.denominator == null && num.denominator == null) || (this.denominator != null && this.denominator.equals(num.denominator));

        if(denominatorEqual && denominatorPolyEqual){   // 分母相等
            return tryToAddNumerator(this,  num);
        }else{
            FactorNumber leftResultNumerator = this.numerator;
            FactorNumber rightResultNumerator = num.numerator;
            PolynomialNumber leftResultNumeratorPolynomial = this.numeratorPolynomial;
            PolynomialNumber rightResultNumeratorPolynomial = num.numeratorPolynomial;

            FactorNumber resultDenominator;
            PolynomialNumber resultDenominatorPolynomial;
            if(denominatorEqual){
                resultDenominator = this.denominator;
            }else{
                resultDenominator = this.denominator;
                if(num.denominator != null){
                    if(resultDenominator == null){
                        resultDenominator = num.denominator;
                    }else{
                        resultDenominator = resultDenominator.mult(num.denominator);
                        rightResultNumerator = rightResultNumerator.mult(num.denominator);
                    }
                    leftResultNumerator = leftResultNumerator.mult(num.denominator);
                }else{
                    rightResultNumerator = rightResultNumerator.mult(resultDenominator);
                }
            }

            if(denominatorPolyEqual){
                resultDenominatorPolynomial = this.denominatorPolynomial;
            }else{
                resultDenominatorPolynomial = this.denominatorPolynomial;
                if(num.denominatorPolynomial != null){
                    if(resultDenominatorPolynomial == null){
                        resultDenominatorPolynomial = num.denominatorPolynomial;
                    }else{
                        resultDenominatorPolynomial = resultDenominatorPolynomial.multiply(num.denominatorPolynomial);
                        if(rightResultNumeratorPolynomial == null){
                            rightResultNumeratorPolynomial = num.denominatorPolynomial;
                        }else{
                            rightResultNumeratorPolynomial = rightResultNumeratorPolynomial.multiply(num.denominatorPolynomial);
                        }
                    }
                    if(leftResultNumeratorPolynomial == null){
                        leftResultNumeratorPolynomial = num.denominatorPolynomial;
                    }else{
                        leftResultNumeratorPolynomial = leftResultNumeratorPolynomial.multiply(num.denominatorPolynomial);
                    }
                }else{
                    rightResultNumeratorPolynomial = rightResultNumeratorPolynomial.multiply(resultDenominatorPolynomial);
                }
            }

            return tryToAddNumerator(new FractionNumber(leftResultNumerator, resultDenominator, leftResultNumeratorPolynomial, resultDenominatorPolynomial),
                    new FractionNumber(rightResultNumerator, resultDenominator, rightResultNumeratorPolynomial, resultDenominatorPolynomial));
        }
    }


    private static FractionNumber tryToAddNumerator(FractionNumber left, FractionNumber right){
        if(left.numerator.equals(right.numerator)){
            PolynomialNumber resultNumeratorPolynomial = left.numeratorPolynomial;
            if(right.numeratorPolynomial != null){
                if(resultNumeratorPolynomial == null){
                    resultNumeratorPolynomial = right.numeratorPolynomial;
                }else{
                    resultNumeratorPolynomial = resultNumeratorPolynomial.add(right.numeratorPolynomial);
                }
            }
            return new FractionNumber(left.numerator, left.denominator, resultNumeratorPolynomial, left.denominatorPolynomial);
        }else if(left.numeratorPolynomial == null && right.numeratorPolynomial == null){
            FactorNumber tryRes = left.numerator.tryAdd(right.numerator);
            if(tryRes != null){
                return new FractionNumber(tryRes, left.denominator, null, null);
            }
        }else{
            PolynomialNumber resultNumeratorPolynomial = null;
            if(left.numeratorPolynomial != null){
                if(FactorNumber.ONE.equals(left.numerator)){
                    resultNumeratorPolynomial = left.numeratorPolynomial;
                }else{
                    FractionNumber tempFraction = new FractionNumber(left.numerator, null, null, null);
                    resultNumeratorPolynomial = left.numeratorPolynomial.multiply(new PolynomialNumber(tempFraction));
                }
            }
            if(right.numeratorPolynomial != null){
                PolynomialNumber midResultPolynomial;
                if(FactorNumber.ONE.equals(right.numerator)){
                    midResultPolynomial = right.numeratorPolynomial;
                }else{
                    FractionNumber tempFraction = new FractionNumber(right.numerator, null, null, null);
                    midResultPolynomial = right.numeratorPolynomial.multiply(new PolynomialNumber(tempFraction));
                }
                if(resultNumeratorPolynomial == null){
                    resultNumeratorPolynomial = midResultPolynomial;
                }else{
                    resultNumeratorPolynomial = resultNumeratorPolynomial.add(midResultPolynomial);
                }
            }
            return new FractionNumber(null, left.denominator, resultNumeratorPolynomial, left.denominatorPolynomial);
        }
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
    public String toDecimal(int count) {
        return null;
    }

    @Override
    public int compareTo(FractionNumber o) {
        return 0;
    }
}
