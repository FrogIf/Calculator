package frog.calculator.math;

import frog.calculator.util.StringUtils;
import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.Itraveller;
import frog.calculator.util.collection.LinkedList;
import frog.calculator.util.collection.UnmodifiableList;

public final class PolynomialNumber extends AbstractCombineIrrationalNumber {

    /*
     * 单一结构式的结构如下:
     * 有理数 + (有理数 * 无理数 * 无理数 ...) / (有理数 * 无理数 * 无理数 * ...) + (有理数 * 无理数 * 无理数 ...) / (有理数 * 无理数 * 无理数 * ...) + ...
     */
    private final LinkedList<Fraction> polynomial = new LinkedList<>();

    // 可以为null
    private final RationalNumber rationalNumber;

    public static PolynomialNumber createPolynomial(AbstractRealNumber realNumber){
        if(realNumber.getClass() == PolynomialNumber.class){
            return (PolynomialNumber) realNumber;
        }else if(realNumber instanceof RationalNumber){
            return new PolynomialNumber((RationalNumber) realNumber);
        }else{
            FactorObject factorObject = realNumber.factorization();
            Product product = new Product(factorObject.getRationalPart(), factorObject.getIrrationalPart());
            Fraction fraction = new Fraction(product, null);
            LinkedList<Fraction> polynomials = new LinkedList<>();
            polynomials.add(fraction);
            return new PolynomialNumber(null, polynomials);
        }
    }

    PolynomialNumber(RationalNumber rationalNumber, LinkedList<Fraction> polynomials) {
        if(polynomials == null || polynomials.isEmpty()){
            throw new IllegalArgumentException("polynomial is empty.");
        }
        Itraveller<Fraction> traveller = polynomials.iterator();
        while(traveller.hasNext()){
            polynomial.add(traveller.next());
        }
        this.rationalNumber = rationalNumber;
    }

    private PolynomialNumber(RationalNumber rationalNumber){
        this.rationalNumber = rationalNumber;
    }


    // 该方法内部不会修改入参数realNumbers
    private static AbstractRealNumber aggregate(RationalNumber rationalNumber, LinkedList<Fraction> realNumbers) {
        RationalNumber resultRational = rationalNumber == null ? RationalNumber.ZERO : rationalNumber;
        LinkedList<Fraction> irrationals = new LinkedList<>();

        Itraveller<Fraction> traveller = realNumbers.iterator();
        while(traveller.hasNext()){
            Fraction fraction = traveller.next();
            RationalNumber rational = fraction.tryConvertToRational();
            if(rational != null){
                resultRational.add(rational);
                continue;
            }
            Iterator<Fraction> irrationalItr = irrationals.iterator();
            boolean unMerge = true;
            while(irrationalItr.hasNext()){
                Fraction irrational = irrationalItr.next();
                Fraction result = fraction.tryAdd(irrational);
                if(result != null){
                    irrationalItr.remove();

                    // 尝试转换为有理数
                    RationalNumber number = result.tryConvertToRational();
                    if(number == null){
                        irrationals.add(result);
                    }else{
                        resultRational = resultRational.add(number);
                    }

                    unMerge = false;
                    break;
                }
            }
            if(unMerge){
                irrationals.add(fraction);
            }
        }

        return createRealNumber(rationalNumber, irrationals);
    }

    private static AbstractRealNumber createRealNumber(RationalNumber rationalNumber, LinkedList<Fraction> polynomial){
        if(polynomial.isEmpty()){
            return rationalNumber;
        }else{
            if(rationalNumber.equals(RationalNumber.ZERO) && polynomial.size() == 1){
                Fraction fraction = polynomial.get(0);
                RationalNumber number = fraction.tryConvertToRational();
                if(number != null){
                    return number;
                }
            }
            return new PolynomialNumber(rationalNumber, polynomial);
        }
    }

    private AbstractRealNumber polynomialAdd(PolynomialNumber num){
        LinkedList<Fraction> realNumbers = new LinkedList<>();
        Iterator<Fraction> iterator = num.polynomial.iterator();
        while(iterator.hasNext()){
            realNumbers.add(iterator.next());
        }
        realNumbers.join(this.polynomial);

        RationalNumber resultRational = this.rationalNumber;
        if(num.rationalNumber != null){
            if(resultRational == null){
                resultRational = num.rationalNumber;
            }else{
                resultRational = resultRational.add(num.rationalNumber);
            }
        }

        return aggregate(resultRational, realNumbers);
    }

    private static void rationNomialMultWithPolynomial(RationalNumber rationalFactor, LinkedList<Fraction> polynomial, LinkedList<Fraction> resultList){
        Itraveller<Fraction> traveller = polynomial.iterator();
        while(traveller.hasNext()){
            Fraction fraction = traveller.next();
            Product product = fraction.numerator;
            RationalNumber rational = product.rationalNumber.mult(rationalFactor);

            Product resProduct = new Product(rational);
            resProduct.factorList = product.factorList;

            resultList.add(new Fraction(resProduct, fraction.denominator));
        }
    }

    private AbstractRealNumber polynomialMult(PolynomialNumber num){
        LinkedList<Fraction> resultPolynomial = new LinkedList<>();

        // 处理有理部分
        RationalNumber resultRational = this.rationalNumber;
        if(resultRational == null){
            resultRational = num.rationalNumber;
        }else if(num.rationalNumber != null){
            resultRational = resultRational.mult(num.rationalNumber);
        }
        if(resultRational == null){
            resultRational = RationalNumber.ZERO;
        }

        if(this.rationalNumber != null){
            rationNomialMultWithPolynomial(this.rationalNumber, num.polynomial, resultPolynomial);
        }

        if(num.rationalNumber != null){
            rationNomialMultWithPolynomial(num.rationalNumber, this.polynomial, resultPolynomial);
        }

        // 处理无理部分
        Iterator<Fraction> leftTraveller = this.polynomial.iterator();
        Itraveller<Fraction> rightTraveller = num.polynomial.iterator();

        while(leftTraveller.hasNext()){
            Fraction leftFraction = leftTraveller.next();
            while(rightTraveller.hasNext()){
                Fraction rightFraction = rightTraveller.next();
                Fraction resultFraction = leftFraction.mult(rightFraction);
                resultPolynomial.add(resultFraction);
            }
        }

        return aggregate(resultRational, resultPolynomial);
    }

    @Override
    public AbstractRealNumber add(AbstractRealNumber num) {
        return this.polynomialAdd(createPolynomial(num));
    }

    @Override
    public AbstractRealNumber sub(AbstractRealNumber num) {
        return this.add(num.not());
    }

    @Override
    public AbstractRealNumber mult(AbstractRealNumber num) {
        return this.polynomialMult(createPolynomial(num));
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

        if(this.rationalNumber != null){
            sb.append(this.rationalNumber.toString());
        }
        if(!this.polynomial.isEmpty()){
            if(sb.length() > 0){
                sb.append('+');
            }
            Itraveller<Fraction> traveller = this.polynomial.iterator();
            sb.append(traveller.next().toString());
            while(traveller.hasNext()){
                sb.append('+');
                sb.append(traveller.next().toString());
            }
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

    private FactorObject factorObject = null;

    @Override
    protected FactorObject factorization() {
        if(this.factorObject == null){
            if(this.polynomial.isEmpty()){
                this.factorObject = new FactorObject(this.rationalNumber, null);
            }else if((this.rationalNumber == null || RationalNumber.ZERO.equals(this.rationalNumber)) && this.polynomial.size() == 1){
                Fraction fraction = this.polynomial.get(0);

                RationalNumber resultRational = null;
                AbstractIrrationalNumber resultIrrational = null;

                if(fraction.numerator.factorList.isEmpty() && fraction.denominator.factorList.isEmpty()){
                    resultRational = fraction.numerator.rationalNumber.div(fraction.denominator.rationalNumber);
                }else{
                    resultIrrational = this;
                }

                this.factorObject = new FactorObject(resultRational, resultIrrational);
            }else{
                this.factorObject = new FactorObject(null, this);
            }
        }
        return this.factorObject;
    }

    @Override
    public AbstractRealNumber not() {
        PolynomialNumber result = new PolynomialNumber(this.rationalNumber);
        Itraveller<Fraction> traveller = this.polynomial.iterator();
        while(traveller.hasNext()){
            Fraction nomial = traveller.next();
            Product product = new Product(nomial.numerator.rationalNumber.not());
            product.factorList = nomial.numerator.factorList;
            Fraction fraction = new Fraction(product, nomial.denominator);
            result.polynomial.add(fraction);
        }
        return result;
    }

    /**
     * 因式
     * rationalNumber和factorList都不为null
     * 内部可以保证factorList对象是不可变的
     */
    private static class Product {

        private static final UnmodifiableList<AbstractIrrationalNumber> Empty_Factor_List = new UnmodifiableList<>(new LinkedList<>());

        private final RationalNumber rationalNumber;

        // 没有使用final修饰, 为提升性能使类内部可以随时为factorList赋值, 需要小心使用, 防止对象破坏不变性
        private UnmodifiableList<AbstractIrrationalNumber> factorList;

        private Product(RationalNumber rationalNumber) {
            if(rationalNumber == null){
                throw new IllegalArgumentException("can't define a empty product.");
            }
            this.factorList = Empty_Factor_List;
            this.rationalNumber = rationalNumber;
        }

        private Product(RationalNumber rational, AbstractIrrationalNumber irrational){
            if(rational == null && irrational == null){
                throw new IllegalArgumentException("can't define a empty product.");
            }
            this.rationalNumber = rational == null ? RationalNumber.ONE : rational;
            LinkedList<AbstractIrrationalNumber> factorList = new LinkedList<>();
            factorList.add(irrational);
            this.factorList = new UnmodifiableList<>(factorList);
        }

        private static void syncNomial(LinkedList<AbstractIrrationalNumber> fromList, LinkedList<AbstractIrrationalNumber> toList){
            Iterator<AbstractIrrationalNumber> iterator = fromList.iterator();
            while(iterator.hasNext()){
                AbstractIrrationalNumber next = iterator.next();
                if(next == null){ continue; }
                toList.add(next);
            }
        }

        private Product not(){
            Product product = new Product(this.rationalNumber.not());
            product.factorList = this.factorList;
            return product;
        }

        private Product tryAdd(Product product){
            if(this.factorList == product.factorList || (this.factorList.isEmpty() && product.factorList.isEmpty())){
                Product result = new Product(this.rationalNumber.add(product.rationalNumber));
                result.factorList = product.factorList;
                return result;
            }else if(this.factorList.size() == product.factorList.size()){
                boolean rationalEql = this.rationalNumber.equals(product.rationalNumber);
                int diff = rationalEql ? 0 : 1;
                AbstractIrrationalNumber diffLeft = null;
                AbstractIrrationalNumber diffRight = null;
                int leftDiffPos = -1;  // 左侧因式不相等因式所在位置
                int[] mark = new int[this.factorList.size()];
                Itraveller<AbstractIrrationalNumber> selfTraveller = this.factorList.iterator();

                int leftIndex = -1;
                while(selfTraveller.hasNext()){
                    leftIndex++;
                    AbstractIrrationalNumber selfNext = selfTraveller.next();
                    int i = 0;
                    AbstractIrrationalNumber next = null;

                    Itraveller<AbstractIrrationalNumber> aimTraveller = product.factorList.iterator();
                    while(aimTraveller.hasNext()){
                        next = aimTraveller.next();
                        if(selfNext.equals(next) && mark[i] == 0){
                            mark[i] = 1;
                            break;
                        }
                        i++;
                    }

                    if(i == mark.length){
                        diff++;
                        if(diff > 1) { return null; }
                        diffLeft = selfNext;
                        diffRight = next;
                        leftDiffPos = leftIndex;
                    }
                }

                if(diffLeft != null){   // 说明有理部分相等, 无理部分存在一个因子不相等
                    AbstractRealNumber tryResult = diffLeft.tryAdd(diffRight);
                    if(tryResult != null){
                        LinkedList<AbstractIrrationalNumber> resultIrrationalList = new LinkedList<>();
                        RationalNumber resultRational = this.rationalNumber;
                        Iterator<AbstractIrrationalNumber> traveller = product.factorList.iterator();
                        int i = 0;
                        while(traveller.hasNext()){
                            if(i == leftDiffPos){
                                traveller.next();
                            }else{
                                resultIrrationalList.add(traveller.next());
                            }
                            i++;
                        }
                        FactorObject factorObject = tryResult.factorization();

                        RationalNumber tryRational = factorObject.getRationalPart();
                        if(tryRational != null){
                            resultRational = resultRational.mult(tryRational);
                        }
                        AbstractIrrationalNumber tryIrrational = factorObject.getIrrationalPart();
                        if(tryIrrational != null){
                            resultIrrationalList.add(tryIrrational);
                        }
                        Product result = new Product(resultRational);
                        result.factorList = new UnmodifiableList<>(resultIrrationalList);
                        return result;
                    }
                }else{  // 说明无理部分完全一致
                    Product result = new Product(this.rationalNumber.add(product.rationalNumber));
                    result.factorList = product.factorList;
                    return result;
                }
            }
            return null;
        }

        private Product multiply(Product product){
            RationalNumber resultRational = this.rationalNumber.mult(product.rationalNumber);
            UnmodifiableList<AbstractIrrationalNumber> resultIrrationalList;

            if(this.factorList.isEmpty()){
                resultIrrationalList = product.factorList;
            }else if(product.factorList.isEmpty()){
                resultIrrationalList = this.factorList;
            }else{
                LinkedList<AbstractIrrationalNumber> newFactorList = new LinkedList<>();
                resultIrrationalList = new UnmodifiableList<>(newFactorList);
                Itraveller<AbstractIrrationalNumber> rightTraveller = product.factorList.iterator();
                while(rightTraveller.hasNext()){
                    resultIrrationalList.add(rightTraveller.next());
                }

                Itraveller<AbstractIrrationalNumber> leftTraveller = this.factorList.iterator();
                while(leftTraveller.hasNext()) {
                    AbstractIrrationalNumber leftIrrational = leftTraveller.next();
                    Iterator<AbstractIrrationalNumber> resultIterator = resultIrrationalList.iterator();
                    while(resultIterator.hasNext()) {
                        AbstractIrrationalNumber rightIrrational = resultIterator.next();
                        AbstractRealNumber real = leftIrrational.tryMult(rightIrrational);
                        if(real != null){
                            resultIterator.remove();
                            FactorObject factorObject = real.factorization();

                            AbstractIrrationalNumber tryIrrational = factorObject.getIrrationalPart();
                            RationalNumber tryRational = factorObject.getRationalPart();
                            if(tryRational != null){
                                resultRational = resultRational.mult(tryRational);
                            }

                            if(tryIrrational != null){
                                resultIrrationalList.add(tryIrrational);
                            }
                        }
                    }
                }
            }

            Product result = new Product(resultRational);
            result.factorList = resultIrrationalList;
            return result;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Product product = (Product) o;
            if(!this.rationalNumber.equals(product.rationalNumber)){
                return false;
            }else{
                if(this.factorList == product.factorList){
                    return true;
                }else {
                    if(this.factorList.size() != product.factorList.size()){
                        return false;
                    }else{
                        int[] mark = new int[this.factorList.size()];
                        Itraveller<AbstractIrrationalNumber> selfTraveller = this.factorList.iterator();
                        while(selfTraveller.hasNext()){
                            AbstractIrrationalNumber selfNext = selfTraveller.next();
                            Itraveller<AbstractIrrationalNumber> aimTraveller = product.factorList.iterator();
                            int i = 0;
                            while(aimTraveller.hasNext()){
                                AbstractIrrationalNumber next = aimTraveller.next();
                                if(selfNext.equals(next) && mark[i] == 0){
                                    mark[i] = 1;
                                    break;
                                }
                                i++;
                            }
                            if(i == mark.length){
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if(rationalNumber != null){
                sb.append(rationalNumber.toString());
            }

            if(!factorList.isEmpty()){
                Itraveller<AbstractIrrationalNumber> traveller = factorList.iterator();
                if(sb.length() > 0){
                    sb.append('*');
                }
                sb.append(traveller.next().toString());
                while(traveller.hasNext()){
                    sb.append('*');
                    sb.append(traveller.next().toString());
                }
            }
            return sb.toString();
        }
    }

    /**
     * 分式
     * 分子不为null, 分母可以为null, 分母为null时表示不是一个分式, 而是由分子组成的整式
     * 不变对象
     */
    private static class Fraction {
        private final Product numerator;
        private final Product denominator;

        private Fraction(Product numerator, Product denominator) {
            if(numerator == null){
                throw new IllegalArgumentException("numerator or denominator can't be null.");
            }
            this.numerator = numerator;
            this.denominator = denominator;
        }

        private static void reduce(LinkedList<AbstractIrrationalNumber> irrationalA, LinkedList<AbstractIrrationalNumber> irrationalB){
            if(!(irrationalA.isEmpty() || irrationalB.isEmpty())){
                Iterator<AbstractIrrationalNumber> topItr = irrationalA.iterator();
                Iterator<AbstractIrrationalNumber> bottomItr = irrationalB.iterator();

                while(topItr.hasNext()){
                    AbstractIrrationalNumber topIrrational = topItr.next();
                    while(bottomItr.hasNext()){
                        AbstractIrrationalNumber bottomIrrational = bottomItr.next();
                        if(topIrrational.equals(bottomIrrational)){
                            topItr.remove();
                            bottomItr.remove();
                            break;
                        }
                    }
                }
            }
        }

        private Fraction tryAdd(Fraction fraction){
            if((this.denominator == null && fraction.denominator == null) || (this.denominator != null && this.denominator.equals(fraction.denominator))){  // 如果分母都为null或者相等
                Product product = this.numerator.tryAdd(fraction.numerator);
                if(product != null){
                    return new Fraction(product, this.denominator);
                }
            }else{
                Product left = fraction.denominator == null ? this.numerator : this.numerator.multiply(fraction.denominator);
                Product right = this.denominator == null ? fraction.numerator : this.denominator.multiply(fraction.numerator);
                Product product = left.tryAdd(right);
                if(product != null){
                    Product resultDenomiator;
                    if(this.denominator == null){
                        resultDenomiator = fraction.denominator;
                    }else if(fraction.denominator == null){
                        resultDenomiator = this.denominator;
                    }else{
                        resultDenomiator = this.denominator.multiply(fraction.denominator);
                    }
                    return new Fraction(product, resultDenomiator);
                }
            }
            return null;
        }

        private RationalNumber tryConvertToRational(){
            if(!this.numerator.factorList.isEmpty()){
                return null;
            }
            if(this.denominator == null){
                return this.numerator.rationalNumber;
            }else{
                if(this.denominator.factorList.isEmpty()){
                    return this.numerator.rationalNumber.div(this.denominator.rationalNumber);
                }else{
                    return null;
                }
            }
        }

        private Fraction not(){
            return new Fraction(this.numerator.not(), this.denominator);
        }

        private Fraction mult(Fraction fraction){
            Product resultNumerator = this.numerator.multiply(fraction.numerator);
            Product resultDenominator = this.denominator.multiply(fraction.denominator);
            return buildFraction(resultNumerator, resultDenominator);
        }

        private Fraction div(Fraction fraction){
            Product resultNumerator = this.numerator.multiply(fraction.denominator);
            Product resultDenominator = this.denominator.multiply(fraction.numerator);
            return buildFraction(resultNumerator, resultDenominator);
        }

        private static Fraction buildFraction(Product numerator, Product denominator){
            LinkedList<AbstractIrrationalNumber> resultTopFactorList = new LinkedList<>();
            Itraveller<AbstractIrrationalNumber> traveller = numerator.factorList.iterator();
            while(traveller.hasNext()){
                resultTopFactorList.add(traveller.next());
            }

            LinkedList<AbstractIrrationalNumber> resultBottomFactorList = new LinkedList<>();
            traveller = denominator.factorList.iterator();
            while(traveller.hasNext()){
                resultBottomFactorList.add(traveller.next());
            }
            reduce(resultTopFactorList, resultBottomFactorList);
            numerator.factorList = new UnmodifiableList<>(resultTopFactorList);
            denominator.factorList = new UnmodifiableList<>(resultBottomFactorList);

            return new Fraction(numerator, denominator);
        }

        @Override
        public String toString() {
            if(denominator != null){
                return StringUtils.concat(numerator.toString(), "/", denominator.toString());
            }else{
                return numerator.toString();
            }
        }
    }


}
