package frog.calculator.math.real;

import frog.calculator.math.irrational.AbstractIrrationalNumber;
import frog.calculator.math.rational.RationalNumber;
import frog.calculator.util.collection.*;

// 因式
public final class FactorNumber extends AbstractStructureNumber implements Comparable<FactorNumber> {

    static final FactorNumber ONE = new FactorNumber(RationalNumber.ONE);

    // 无理数因子
    private UnmodifiableList<AbstractIrrationalNumber> irrationalFactor;

    // 有理数因子, 不为null
    private final RationalNumber rationalFactor;

    private FactorNumber(RationalNumber rationalFactor){
        if(rationalFactor == null){
            throw new IllegalArgumentException("can't create empty factor number.");
        }
        this.rationalFactor = rationalFactor;
    }

    public FactorNumber(RationalNumber rationalFactor, AbstractIrrationalNumber irrationalNumber){
        if(rationalFactor == null){
            rationalFactor = RationalNumber.ONE;
        }
        this.rationalFactor = rationalFactor;
        if(irrationalNumber != null){
            LinkedList<AbstractIrrationalNumber> irrationals = new LinkedList<>();
            irrationals.add(irrationalNumber);
            this.irrationalFactor = new UnmodifiableList<>(irrationals);
        }
    }

//    FactorNumber(RationalNumber rationalFactor, IList<AbstractIrrationalNumber> irrationalFactor) {
//        if(rationalFactor == null){
//            rationalFactor = RationalNumber.ONE;
//        }
//        this.rationalFactor = rationalFactor;
//        if(irrationalFactor != null && !irrationalFactor.isEmpty()){
//            LinkedList<AbstractIrrationalNumber> irrationalList = new LinkedList<>();
//            ITraveller<AbstractIrrationalNumber> traveller = irrationalFactor.iterator();
//            while(traveller.hasNext()){
//                irrationalList.add(traveller.next());
//            }
//            this.irrationalFactor = new UnmodifiableList<>(irrationalList);
//        }
//    }

    @Override
    public FactorNumber not() {
        FactorNumber result = new FactorNumber(this.rationalFactor.not());
        result.irrationalFactor = this.irrationalFactor;
        return result;
    }

    FactorNumber tryAdd(FactorNumber num) {
        if((this.irrationalFactor == null || this.irrationalFactor.isEmpty())
            && (num.irrationalFactor == null || num.irrationalFactor.isEmpty())){
            return new FactorNumber(this.rationalFactor.mult(num.rationalFactor));
        }else if(this.irrationalFactor != null && num.irrationalFactor != null && this.irrationalFactor.size() == num.irrationalFactor.size()){
            int unMatch = 0;
            byte[] markArr = new byte[this.irrationalFactor.size()];
            ITraveller<AbstractIrrationalNumber> leftTraveller = this.irrationalFactor.iterator();
            AbstractIrrationalNumber diffLeft = null;
            while(leftTraveller.hasNext()){
                AbstractIrrationalNumber leftIrrational = leftTraveller.next();
                boolean match = false;
                int i = 0;
                ITraveller<AbstractIrrationalNumber> rightTraveller = num.irrationalFactor.iterator();
                while(rightTraveller.hasNext()){
                    AbstractIrrationalNumber rightIrrational = rightTraveller.next();
                    if(markArr[i] == 0){
                        if(leftIrrational.equals(rightIrrational)){
                            match = true;
                            markArr[i] = 1;
                            break;
                        }
                    }
                    i++;
                }
                if(!match){
                    unMatch++;
                    if(unMatch > 1){
                        break;
                    }
                    diffLeft = leftIrrational;
                }
            }

            if(unMatch == 0){
                FactorNumber result = new FactorNumber(this.rationalFactor.add(num.rationalFactor));
                result.irrationalFactor = this.irrationalFactor;
                return result;
            }else if(unMatch == 1){
                ITraveller<AbstractIrrationalNumber> rightTraveller = num.irrationalFactor.iterator();
                LinkedList<AbstractIrrationalNumber> resultIrrationalFactor = new LinkedList<>();
                int i = 0;
                AbstractIrrationalNumber diffRight = null;
                while (rightTraveller.hasNext()){
                    AbstractIrrationalNumber next = rightTraveller.next();
                    if(markArr[i] == 0){
                        diffRight = next;
                    }else{
                        resultIrrationalFactor.add(next);
                    }
                    i++;
                }

                FactorNumber resultFactor = null;
                if(this.rationalFactor.equals(num.rationalFactor)){
                    FactorNumber tryResult = diffLeft.tryAdd(diffRight);
                    if(tryResult != null){
                        resultFactor = new FactorNumber(this.rationalFactor.mult(tryResult.rationalFactor));
                        resultFactor.irrationalFactor = tryResult.irrationalFactor;
                    }
                }else{
                    AbstractIrrationalNumber leftTryAbsorb = diffLeft.tryAbsorb(this.rationalFactor);
                    if(leftTryAbsorb != null){
                        assert diffRight != null;
                        AbstractIrrationalNumber rightTryAbsorb = diffRight.tryAbsorb(num.rationalFactor);
                        if(rightTryAbsorb != null){
                            resultFactor = leftTryAbsorb.tryAdd(rightTryAbsorb);
                        }
                    }
                }

                if(resultFactor != null){
                    FactorNumber result = new FactorNumber(resultFactor.rationalFactor);
                    if(resultFactor.irrationalFactor != null && !resultFactor.irrationalFactor.isEmpty()){
                        ITraveller<AbstractIrrationalNumber> traveller = resultFactor.irrationalFactor.iterator();
                        while(traveller.hasNext()){
                            resultIrrationalFactor.add(traveller.next());
                        }
                    }
                    result.irrationalFactor = new UnmodifiableList<>(resultIrrationalFactor);
                    return result;
                }
            }
        }
        return null;
    }

    FactorNumber mult(FactorNumber num) {
        RationalNumber resultRational = this.rationalFactor.mult(num.rationalFactor);


        boolean leftExists = this.irrationalFactor != null && !this.irrationalFactor.isEmpty();
        boolean rightExists = num.irrationalFactor != null && !num.irrationalFactor.isEmpty();

        if(leftExists && rightExists){
            return aggregate(resultRational, this.irrationalFactor, num.irrationalFactor);
        }else{
            FactorNumber resultFactor = new FactorNumber(resultRational);
            if(leftExists){
                resultFactor.irrationalFactor = this.irrationalFactor;
            }else if(rightExists){
                resultFactor.irrationalFactor = num.irrationalFactor;
            }
            return resultFactor;
        }
    }

    private static FactorNumber aggregate(RationalNumber rational, IList<AbstractIrrationalNumber> left, IList<AbstractIrrationalNumber> right){
        return null;
    }

    // 聚合所有因子, 提取出有理数因子, 将可以相乘的无理数乘在一起
    private static RationalNumber aggregateSelf(IList<AbstractIrrationalNumber> irrationalFactor){
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
        if(this.irrationalFactor == null || this.irrationalFactor.isEmpty()){
            return this.rationalFactor;
        }
        return null;
    }

    @Override
    public String toDecimal(int count) {
        return null;
    }

    @Override
    public int compareTo(FactorNumber o) {
        return 0;
    }
}
