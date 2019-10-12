package frog.calculator.math.real;

import frog.calculator.math.rational.RationalNumber;
import frog.calculator.util.collection.ITraveller;
import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.LinkedList;

// 多项式
public final class PolynomialNumber extends AbstractStructureNumber implements Comparable<PolynomialNumber>{

    // 有理项
    private final RationalNumber rationalNomial;

    // 分式项
    private LinkedList<FractionNumber> fractionNomial;

    private PolynomialNumber(RationalNumber rationalNomial) {
        this.rationalNomial = rationalNomial;
    }

    public PolynomialNumber(RationalNumber rationalNumber, LinkedList<FractionNumber> fractionNomial){
        RationalNumber resultRational = rationalNumber;
        if(fractionNomial != null && !fractionNomial.isEmpty()){
            LinkedList<FractionNumber> temp = new LinkedList<>();
            ITraveller<FractionNumber> traveller = fractionNomial.iterator();
            while(traveller.hasNext()){
                temp.add(traveller.next());
            }
            RationalNumber rational = aggregateSelf(temp);
            if(rational != null){
                if(resultRational == null){
                    resultRational = rational;
                }else{
                    resultRational = rationalNumber.add(rational);
                }
            }
            this.fractionNomial = temp;
        }
        this.rationalNomial = resultRational;
        if(this.rationalNomial == null && (this.fractionNomial == null || this.fractionNomial.isEmpty())){
            throw new IllegalArgumentException("can't define a empty polynomial");
        }
    }

    PolynomialNumber(FractionNumber fractionNumber){
        if(fractionNumber == null){
            throw new IllegalArgumentException("can't define a empty polynomial");
        }
        this.rationalNomial = null;
        this.fractionNomial = new LinkedList<>();
        this.fractionNomial.add(fractionNumber);
    }

    /**
     * if fractionNomial is null, will throw NullPointerException
     */
    private static PolynomialNumber aggregate(RationalNumber rationalNumber, LinkedList<FractionNumber> fractionNomial){
        RationalNumber resultRational = rationalNumber == null ? RationalNumber.ZERO : rationalNumber;

        LinkedList<FractionNumber> resultFractionNomial = null;

        if(fractionNomial != null && !fractionNomial.isEmpty()){
            resultFractionNomial = new LinkedList<>();
            Iterator<FractionNumber> resultIterator = resultFractionNomial.iterator();

            ITraveller<FractionNumber> traveller = fractionNomial.iterator();

            boolean unMerge = true;
            while(traveller.hasNext()){
                FractionNumber next = traveller.next();
                while(resultIterator.hasNext()){
                    FractionNumber res = resultIterator.next();
                    FractionNumber tryResult = next.tryAdd(res);
                    if(tryResult != null){
                        unMerge = false;
                        resultIterator.remove();

                        RationalNumber rational = tryResult.tryConvertToRational();
                        if(rational == null){
                            resultFractionNomial.add(tryResult);
                        }else{
                            resultRational = resultRational.add(rational);
                        }

                        break;
                    }
                }

                if(unMerge){
                    resultFractionNomial.add(next);
                }
                unMerge = true;
            }

            if(resultFractionNomial.size() < fractionNomial.size()){
                RationalNumber number = aggregateSelf(resultFractionNomial);
                resultRational = resultRational.add(number);
            }
        }

        PolynomialNumber result = new PolynomialNumber(resultRational);
        result.fractionNomial = resultFractionNomial;
        return result;
    }

    private static RationalNumber aggregateSelf(LinkedList<FractionNumber> fractionNomial){
        RationalNumber rationalResult = RationalNumber.ZERO;
        LinkedList<FractionNumber> tempList = new LinkedList<>();

        LinkedList<FractionNumber> cursor = fractionNomial;
        LinkedList<FractionNumber> tempResult = tempList;
        ITraveller<FractionNumber> outerTraveller = cursor.iterator();

        int i = 0;
        boolean merge = false;
        while(outerTraveller.hasNext()){
            int j = 0;
            FractionNumber frL = outerTraveller.next();
            Iterator<FractionNumber> innerItr = cursor.iterator();
            while(innerItr.hasNext()){
                FractionNumber frR = innerItr.next();
                if(i == j) { continue; }

                FractionNumber tryRes = frL.tryAdd(frR);
                if(tryRes != null){
                    merge = true;
//                    innerItr.remove();

                    RationalNumber tryRational = tryRes.tryConvertToRational();
                    if(tryRational != null){
                        rationalResult = rationalResult.add(tryRational);
                        if(innerItr.hasNext()){
                            frL = innerItr.next();
                        }
                    }else{
                        frL = tryRes;
                        tempResult.add(tryRes);
                    }
                }else{
                    tempResult.add(frR);
                }

                j++;
            }

            if(merge){
                LinkedList<FractionNumber> temp = cursor;
                cursor = tempResult;
                tempResult = temp;

                tempResult.clear();

                outerTraveller = cursor.iterator();
                merge = false;
                i = 0;
            }else{
                i++;
            }
        }

        if(cursor != fractionNomial){
            fractionNomial.clear();
            ITraveller<FractionNumber> traveller = cursor.iterator();
            while(traveller.hasNext()){
                fractionNomial.add(traveller.next());
            }
        }
        return rationalResult;
    }

    public PolynomialNumber add(PolynomialNumber num){
        RationalNumber resultRational = this.rationalNomial;
        if(num.rationalNomial != null){
            if(resultRational != null){
                resultRational = resultRational.add(num.rationalNomial);
            }else{
                resultRational = num.rationalNomial;
            }
        }

        LinkedList<FractionNumber> temp;
        if(this.fractionNomial != null){
            temp = new LinkedList<>();
            Iterator<FractionNumber> iterator = this.fractionNomial.iterator();
            while(iterator.hasNext()){
                temp.add(iterator.next());
            }
            if(num.fractionNomial != null){
                temp.join(num.fractionNomial);
            }
        }else{
            temp = num.fractionNomial;
        }

        return aggregate(resultRational, temp);
    }

    public PolynomialNumber subtract(PolynomialNumber num){
        return this.add(num.not());
    }

    public PolynomialNumber multiply(PolynomialNumber num){
        RationalNumber resultRational = this.rationalNomial;
        if(num.rationalNomial != null){
            if(resultRational != null){
                resultRational = resultRational.mult(num.rationalNomial);
            }else{
                resultRational = num.rationalNomial;
            }
        }

        LinkedList<FractionNumber> resultFraction = null;
        if(this.fractionNomial != null || num.fractionNomial != null){
            resultFraction = new LinkedList<>();
            if(this.fractionNomial != null && num.rationalNomial != null){
                ITraveller<FractionNumber> leftTraveller = this.fractionNomial.iterator();
                while(leftTraveller.hasNext()){
                    FractionNumber nomial = leftTraveller.next();
                    resultFraction.add(nomial.mult(num.rationalNomial));
                }
            }
            if(num.fractionNomial != null && this.rationalNomial != null){
                ITraveller<FractionNumber> rightTraveller = num.fractionNomial.iterator();
                while(rightTraveller.hasNext()){
                    FractionNumber nomial = rightTraveller.next();
                    resultFraction.add(nomial.mult(this.rationalNomial));
                }
            }
            if(this.fractionNomial != null && num.fractionNomial != null){
                ITraveller<FractionNumber> leftTraveller = this.fractionNomial.iterator();
                while(leftTraveller.hasNext()){
                    FractionNumber left = leftTraveller.next();
                    ITraveller<FractionNumber> rightTraveller = num.fractionNomial.iterator();
                    while(rightTraveller.hasNext()){
                        resultFraction.add(left.mult(rightTraveller.next()));
                    }
                }
            }

            RationalNumber rationalNumber = aggregateSelf(resultFraction);
            if(rationalNumber != null){
                if(resultRational != null){
                    resultRational = resultRational.add(rationalNumber);
                }else{
                    resultRational = rationalNumber;
                }
            }
        }

        PolynomialNumber polynomial = new PolynomialNumber(resultRational);
        polynomial.fractionNomial = resultFraction;
        return polynomial;
    }

    public PolynomialNumber divide(PolynomialNumber num) {
        RationalNumber resultRational = null;
        LinkedList<FractionNumber> resultFractionList = null;

        if((this.fractionNomial == null || this.fractionNomial.isEmpty()) && (num.fractionNomial == null || num.fractionNomial.isEmpty())){
            resultRational = this.rationalNomial.div(num.rationalNomial);
        }else if(num.fractionNomial == null || num.fractionNomial.isEmpty()){
            resultRational = this.rationalNomial == null ? RationalNumber.ONE.div(num.rationalNomial) : this.rationalNomial.div(num.rationalNomial);
            ITraveller<FractionNumber> traveller = this.fractionNomial.iterator();
            resultFractionList = new LinkedList<>();
            while(traveller.hasNext()){
                FractionNumber fraction = traveller.next();
                resultFractionList.add(fraction.div(this.rationalNomial));
            }
        }else if(this.rationalNomial == null && num.rationalNomial == null && this.fractionNomial.size() == 1 && num.fractionNomial.size() == 1){
            FractionNumber fraction = this.fractionNomial.get(0).div(num.fractionNomial.get(0));
            RationalNumber rational = fraction.tryConvertToRational();
            if(rational != null){
                resultRational = rational;
            }else{
                resultFractionList = new LinkedList<>();
                resultFractionList.add(fraction);
            }
        }else{
            return new PolynomialNumber(new FractionNumber(null,null, this, num));
        }

        PolynomialNumber polynomialResult = new PolynomialNumber(resultRational);
        polynomialResult.fractionNomial = resultFractionList;
        return polynomialResult;
    }

    @Override
    protected RationalNumber tryConvertToRational(){
        if (fractionNomial == null || fractionNomial.isEmpty()) {
            return this.rationalNomial;
        }
        return null;
    }


    @Override
    public PolynomialNumber not() {
        RationalNumber resultRational = this.rationalNomial == null ? RationalNumber.N_ONE : this.rationalNomial.not();
        LinkedList<FractionNumber> resultFractionalNomial = new LinkedList<>();

        PolynomialNumber result = new PolynomialNumber(resultRational);
        if(this.fractionNomial != null && !this.fractionNomial.isEmpty()){
            ITraveller<FractionNumber> traveller = this.fractionNomial.iterator();
            while(traveller.hasNext()){
                resultFractionalNomial.add(traveller.next().not());
            }
            result.fractionNomial = fractionNomial;
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(rationalNomial != null){
            sb.append(rationalNomial.toString());
        }
        if(fractionNomial != null && !fractionNomial.isEmpty()){
            if(rationalNomial != null){
                sb.append('+');
            }
            ITraveller<FractionNumber> traveller = fractionNomial.iterator();
            sb.append(traveller.next());
            while(traveller.hasNext()){
                sb.append('+');
                sb.append(traveller.next().toString());
            }
        }

        return sb.toString();
    }

    @Override
    public String toDecimal(int count) {
        return null;
    }

    @Override
    public int compareTo(PolynomialNumber o) {
        return 0;
    }
}
