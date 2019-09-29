package frog.calculator.math;

import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.Itraveller;
import frog.calculator.util.collection.LinkedList;

public final class PolynomialNumber extends AbstractIrrationalNumber {

    private final LinkedList<RealNumber> nomials = new LinkedList<>();

    public static PolynomialNumber createPolynomial(AbstractRealNumber realNumber){
        if(realNumber instanceof PolynomialNumber){
            return (PolynomialNumber) realNumber;
        }else if(realNumber instanceof RealNumber){
            return new PolynomialNumber((RealNumber) realNumber);
        }else{
            throw new IllegalArgumentException("Real Numbers that cannot be processed.");
        }
    }

    public PolynomialNumber(RealNumber realNumber){
        if(realNumber == null){
            throw new IllegalArgumentException("nomial is null.");
        }
        this.nomials.add(realNumber);
    }

    PolynomialNumber(LinkedList<RealNumber> polynomials) {
        if(polynomials == null || polynomials.isEmpty()){
            throw new IllegalArgumentException("polynomial is empty.");
        }
        Iterator<RealNumber> iterator = polynomials.iterator();
        while(iterator.hasNext()){
            nomials.add(iterator.next());
        }
    }

    private PolynomialNumber(){ }

    private AbstractRealNumber add(PolynomialNumber num){
        LinkedList<RealNumber> realNumbers = new LinkedList<>();
        Iterator<RealNumber> iterator = num.nomials.iterator();
        while(iterator.hasNext()){
            realNumbers.add(iterator.next());
        }
        realNumbers.join(this.nomials);
        return aggregate(realNumbers);
    }

    // 改方法内部不会修改出入参数realNumbers
    private static AbstractRealNumber aggregate(LinkedList<RealNumber> realNumbers) {
        RationalNumber rationalPart = RationalNumber.ZERO;
        LinkedList<RealNumber> irrationals = new LinkedList<>();    // 循环结束前, 这里面存入的一定是存在无理数的项
        Itraveller<RealNumber> traveller = realNumbers.iterator();
        while(traveller.hasNext()){
            RealNumber nomial = traveller.next();
            if(nomial == RationalNumber.ZERO){ continue; }

            AbstractIrrationalNumber irrationalNumber = nomial.getIrrationalPart();
            if(irrationalNumber == null){                       // 如果该项是一个有理数项
                rationalPart = rationalPart.add(nomial.getRationalPart());
            }else{                                              // 如果该项是一个无理数项
                RationalNumber rp = nomial.getRationalPart();
                Iterator<RealNumber> iterator = irrationals.iterator();
                boolean unMerge = true;
                while(iterator.hasNext()){
                    AbstractRealNumber number = iterator.next();
                    RationalNumber nextRp = number.getRationalPart();
                    if((rp == null && nextRp == null) || (rp != null && rp.equals(nextRp))){
                        AbstractRealNumber tryRes = number.getIrrationalPart().tryAdd(irrationalNumber);    // 这里number不用判空, 因为他是从realNums中取出的, 所以无理数部分肯定不为空
                        if(tryRes != null){
                            iterator.remove();
                            AbstractIrrationalNumber tryIrrational = tryRes.getIrrationalPart();
                            RationalNumber tryRational = tryRes.getRationalPart();
                            if(tryIrrational != null){
                                if(tryRational != null){
                                    rp = rp == null ? tryRational : rp.mult(tryRational);
                                }
                                irrationals.add(new RealNumber(rp, tryIrrational));
                            }else{
                                // 如果这里tryRes.getRationalPart()为null, 那么上面的运算肯定是不正确的, 因为两个实数相加得null是不可能的. 这里就不捕捉异常了, 直接抛空指针吧
                                rationalPart = tryRes.getRationalPart().add(rationalPart);
                            }
                            unMerge = false;
                            break;
                        }
                    }
                }
                if(unMerge){
                    irrationals.add(nomial);
                }
            }
        }

        if(irrationals.isEmpty()){
            return rationalPart;
        }else{
            if(rationalPart != RationalNumber.ZERO) {
                irrationals.add(rationalPart);
            }else if(irrationals.size() == 1){
                return irrationals.get(0);
            }
            return new PolynomialNumber(irrationals);
        }
    }

    @Override
    public RationalNumber getRationalPart() {
        return null;
    }

    @Override
    public AbstractIrrationalNumber getIrrationalPart() {
        return this;
    }

    @Override
    public AbstractRealNumber add(AbstractRealNumber num) {
        return this.add(createPolynomial(num));
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
    public String toDecimal(int count) {
        return null;
    }

    @Override
    public int compareTo(INumber o) {
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<RealNumber> iterator = this.nomials.iterator();
        sb.append(iterator.next().toString());
        while(iterator.hasNext()){
            sb.append('+');
            sb.append(iterator.next().toString());
        }
        return sb.toString();
    }

    @Override
    public AbstractRealNumber tryAdd(AbstractIrrationalNumber num) {
        return this.add(num);
    }

    @Override
    public AbstractRealNumber trySub(AbstractIrrationalNumber num) {
        return this.add(num.not());
    }

    @Override
    public AbstractRealNumber tryMult(AbstractIrrationalNumber num) {
        return null;
    }

    @Override
    public AbstractRealNumber tryDiv(AbstractIrrationalNumber num) {
        return null;
    }

    @Override
    public AbstractRealNumber not() {
        PolynomialNumber result = new PolynomialNumber();
        Itraveller<RealNumber> traveller = this.nomials.iterator();
        while(traveller.hasNext()){
            RealNumber nomial = traveller.next();
            result.nomials.add(nomial.not());
        }
        return result;
    }
}
