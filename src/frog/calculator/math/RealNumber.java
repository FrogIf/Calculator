package frog.calculator.math;

import frog.calculator.util.collection.LinkedList;

public class RealNumber extends AbstractRealNumber {

    /*
     * 一个实数 = 有理数 * 无理数
     * 也就是一个多项式
     */

    private final RationalNumber rationalPart;

    private final AbstractIrrationalNumber irrationalPart;

    RealNumber(RationalNumber rationalPart, AbstractIrrationalNumber irrationalNumber) {
        this.rationalPart = rationalPart;
        this.irrationalPart = irrationalNumber;
    }

    protected RealNumber() {
        this.rationalPart = null;
        this.irrationalPart = null;
    }

    @Override
    public String toDecimal(int count) {
        return null;
    }

    @Override
    public int compareTo(INumber o) {
        return 0;
    }

    public RationalNumber getRationalPart() {
        return rationalPart;
    }

    public AbstractIrrationalNumber getIrrationalPart() {
        return irrationalPart;
    }

    @Override
    public AbstractRealNumber add(AbstractRealNumber num) {
        if(num instanceof RealNumber){
            AbstractIrrationalNumber leftIrrational = this.getIrrationalPart();
            AbstractIrrationalNumber rightIrrational = num.getIrrationalPart();

            RationalNumber leftRational = this.getRationalPart();
            RationalNumber rightRational = num.getRationalPart();

            if(leftIrrational == null && rightIrrational == null){       // 有理数 + 有理数
                return leftRational.add(rightRational);
            }else if(leftIrrational != null && rightIrrational != null){ // 无理数 + 无理数
                if((leftRational == null && rightRational == null) || (leftRational != null && leftRational.equals(rightRational))) {
                    AbstractRealNumber tryRes = leftIrrational.tryAdd(rightIrrational);
                    if(tryRes != null){   // 可合并
                        AbstractIrrationalNumber resultIrrational = null;
                        RationalNumber resultRational = leftRational;
                        RationalNumber tryRation = tryRes.getRationalPart();
                        AbstractIrrationalNumber irrationalPart = tryRes.getIrrationalPart();
                        if(tryRation != null){
                            if(resultRational != null){
                                resultRational = resultRational.mult(tryRation);
                            }else{
                                resultRational = tryRation;
                            }
                        }
                        if(irrationalPart != null){

                        }
                        return new RealNumber(resultRational, resultIrrational);
                    }
                }

                // 不可合并的情况下会执行这里
                LinkedList<RealNumber> polynomial = new LinkedList<>();
                polynomial.add(this);
                polynomial.add((RealNumber) num);
                return new PolynomialNumber(polynomial);
            }else{                                                      // 有理数 + 无理数
                LinkedList<RealNumber> polynomial = new LinkedList<>();
                polynomial.add(this);
                polynomial.add((RealNumber) num);
                return new PolynomialNumber(polynomial);
            }
        }else{
            return PolynomialNumber.createPolynomial(this).add(num);
        }
    }

    @Override
    public AbstractRealNumber sub(AbstractRealNumber num) {
        return null;
    }

    @Override
    public AbstractRealNumber mult(AbstractRealNumber num) {
        return null;
    }

    @Override
    public AbstractRealNumber div(AbstractRealNumber num) {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if(this.rationalPart != null){
            result.append(this.rationalPart.toString());
        }
        if(this.irrationalPart != null){
            if(result.length() > 0) { result.append('*'); }
            result.append(this.irrationalPart.toString());
        }
        return result.toString();
    }
}
