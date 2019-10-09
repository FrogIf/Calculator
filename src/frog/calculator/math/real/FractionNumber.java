package frog.calculator.math.real;

import frog.calculator.math.rational.RationalNumber;

// 分式
public final class FractionNumber extends AbstractStructureNumber implements Comparable<FractionNumber> {

    // 分子一定不为null
    private final FactorNumber numerator;

    private final FactorNumber denominator;

    private FractionNumber(FactorNumber numerator, FactorNumber denominator) {
        if(numerator == null){
            throw new IllegalArgumentException("numerator is null.");
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }

    protected FactorNumber getNumerator(){
        return this.numerator;
    }

    protected FactorNumber getDenominator(){
        return this.denominator;
    }


    public FactorNumber tryConvertToFactor(){
        return null;
    }

    public RationalNumber tryConvertToRational(){
        return null;
    }

    @Override
    public FractionNumber not() {
        return new FractionNumber(this.numerator.not(), this.denominator);
    }

    @Override
    public String toDecimal(int count) {
        return null;
    }

    @Override
    public int compareTo(FractionNumber o) {
        return 0;
    }

    public FractionNumber tryAdd(FractionNumber num) {
        return null;
    }

    public FractionNumber trySub(FractionNumber num) {
        return null;
    }

    public FractionNumber mult(FractionNumber num) {
        return null;
    }

    public FractionNumber tryDiv(FractionNumber num) {
        return null;
    }

    public FractionNumber mult(RationalNumber rational){
        return new FractionNumber(this.numerator.mult(rational), this.denominator);
    }

    public FractionNumber div(RationalNumber rational){
        return new FractionNumber(this.numerator.div(rational), this.denominator);
    }
}
