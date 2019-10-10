package frog.calculator.math.real;

import frog.calculator.math.irrational.AbstractIrrationalNumber;
import frog.calculator.math.rational.RationalNumber;
import frog.calculator.util.collection.*;

// 因式
final class FactorNumber extends AbstractStructureNumber implements Comparable<FactorNumber> {

    static final FactorNumber ONE = new FactorNumber(RationalNumber.ONE, null);

    // 无理数因子
    private UnmodifiableList<AbstractIrrationalNumber> irrationalFactor;

    // 有理数因子, 不为null
    private final RationalNumber rationalFactor;

    private FactorNumber(RationalNumber rationalFactor){
        this(rationalFactor, null);
    }

    FactorNumber(RationalNumber rationalFactor, IList<AbstractIrrationalNumber> irrationalFactor) {
        if(rationalFactor == null){
            rationalFactor = RationalNumber.ONE;
        }
        this.rationalFactor = rationalFactor;
        if(irrationalFactor != null && !irrationalFactor.isEmpty()){
            LinkedList<AbstractIrrationalNumber> irrationalList = new LinkedList<>();
            ITraveller<AbstractIrrationalNumber> traveller = irrationalFactor.iterator();
            while(traveller.hasNext()){
                irrationalList.add(traveller.next());
            }
            this.irrationalFactor = new UnmodifiableList<>(irrationalList);
        }
    }

    // 聚合所有因子, 提取出有理数因子, 将可以相乘的无理数乘在一起
    private static RationalNumber aggregate(RationalNumber rationalFactor, IList<AbstractIrrationalNumber> irrationalFactor){
        RationalNumber resultRational = rationalFactor == null ? RationalNumber.ONE : rationalFactor;

        if(irrationalFactor != null && !irrationalFactor.isEmpty()){
            Iterator<AbstractIrrationalNumber> iterator = irrationalFactor.iterator();
            while(iterator.hasNext()){
                AbstractIrrationalNumber irrationalA = iterator.next();
                while(iterator.hasNext()){
                    AbstractIrrationalNumber irrationalB = iterator.next();
                    PolynomialNumber tryRes = irrationalA.tryMult(irrationalB);
                    if(tryRes != null){
                        iterator.remove();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public FactorNumber not() {
        FactorNumber result = new FactorNumber(this.rationalFactor.not());
        result.irrationalFactor = this.irrationalFactor;
        return result;
    }

    @Override
    public String toDecimal(int count) {
        return null;
    }

    @Override
    public int compareTo(FactorNumber o) {
        return 0;
    }

    public FactorNumber tryAdd(FactorNumber num) {
        return null;
    }

    public FactorNumber mult(FactorNumber num) {
        return null;
    }

    public FactorNumber div(FactorNumber num) {
        return null;
    }

    public FactorNumber mult(RationalNumber num){
        RationalNumber resultRational = this.rationalFactor.mult(num);
        FactorNumber factorNumber = new FactorNumber(resultRational);
        factorNumber.irrationalFactor = this.irrationalFactor;
        return factorNumber;
    }

    public FactorNumber div(RationalNumber num){
        FactorNumber factorNumber = new FactorNumber(this.rationalFactor.div(num));
        factorNumber.irrationalFactor = this.irrationalFactor;
        return factorNumber;
    }

    @Override
    protected RationalNumber tryConvertToRational() {
        return null;
    }
}
