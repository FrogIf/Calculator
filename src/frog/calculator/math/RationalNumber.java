package frog.calculator.math;

import frog.calculator.util.StringUtils;
import frog.calculator.util.collection.LinkedList;

/**
 * 有理数
 */
public final class RationalNumber extends AbstractRealNumber {

    private final IntegerNumber numerator;    // 分子

    private final IntegerNumber denominator;  // 分母

    public static final RationalNumber ZERO = new RationalNumber(IntegerNumber.ZERO, IntegerNumber.ONE);

    public static final RationalNumber ONE = new RationalNumber(IntegerNumber.ONE, IntegerNumber.ONE);

    public static final RationalNumber N_ONE = new RationalNumber(IntegerNumber.ONE.not(), IntegerNumber.ONE);

    private RationalNumber(IntegerNumber numerator, IntegerNumber denominator){
        this.denominator = denominator.abs();
        byte sign = (byte) (numerator.getSign() ^ denominator.getSign());
        this.numerator = sign == numerator.getSign() ? numerator : numerator.not();
    }

    public RationalNumber(String numerator, String denominator){
        IntegerNumber top;
        IntegerNumber bottom;

        if(StringUtils.isNotBlank(numerator)){
            top = IntegerNumber.convertToInteger(numerator);
        }else{
            top = IntegerNumber.ZERO;
        }
        if(StringUtils.isNotBlank(denominator)){
            bottom = IntegerNumber.convertToInteger(denominator);
        }else{
            bottom = IntegerNumber.ONE;
        }

        IntegerNumber gcd = bottom.greatestCommonDivisor(top);
        if(gcd != IntegerNumber.ONE){
            top = top.div(gcd);
            bottom = bottom.div(gcd);
        }

        byte sign = (byte) (top.getSign() ^ bottom.getSign());
        this.numerator = top.getSign() == sign ? top : top.not();
        this.denominator = bottom.abs();
    }

    public RationalNumber(String decimal){
        if(StringUtils.isBlank(decimal)){
            throw new IllegalArgumentException("decimal is blank.");
        }
        int pos = decimal.indexOf(".");
        IntegerNumber top;
        IntegerNumber bottom = IntegerNumber.ONE;
        if(pos < 0){
            top = IntegerNumber.convertToInteger(decimal);
        }else{
            top = IntegerNumber.convertToInteger(decimal.replace(".", ""));

            int len = decimal.length() - pos - 1;
            StringBuilder denominator = new StringBuilder(len + 1);
            denominator.append("1");
            for(int i = 0; i < len; i++){
                denominator.append('0');
            }
            bottom = IntegerNumber.convertToInteger(denominator.toString());

            IntegerNumber gcd = bottom.greatestCommonDivisor(top);
            if(gcd != IntegerNumber.ONE){
                top = top.div(gcd);
                bottom = bottom.div(gcd);
            }
        }
        byte sign = (byte) (top.getSign() ^ bottom.getSign());
        this.numerator = top.getSign() == sign ? top : top.not();
        this.denominator = bottom.abs();
    }

    /**
     * 无限循环小数 <br/>
     * 需要注意的是, repetend后面的部分会被认为是一个完整的循环节
     * @param decimal 小数
     * @param repetend 小数循环节开始的位置(从0计)
     */
    public RationalNumber(String decimal, int repetend){
        if(StringUtils.isBlank(decimal)){
            throw new IllegalArgumentException("decimal is blank.");
        }

        int pos = decimal.indexOf(".");
        if(pos < 0){
            this.numerator = IntegerNumber.convertToInteger(decimal);
            this.denominator = IntegerNumber.ONE;
        }else{
            int start = pos + repetend + 1;
            if(start >= decimal.length()){
                throw new IllegalArgumentException("decimal is not enough.");
            }

            boolean negative = decimal.startsWith("-");
            decimal = decimal.replace(".", "");
            if(negative){
                decimal = decimal.substring(1);
            }
            start--;
            repetend--;
            /*
             * 循环小数转分数:
             * 123.456 (56循环)
             * --> 分子 = 123456 - 1234
             *     分母 = 990 (9的个数与循环节位数相同, 0的位数与不循环部分相同)
             */
            IntegerNumber top;
            IntegerNumber bottom;
            IntegerNumber a = IntegerNumber.convertToInteger(decimal);
            IntegerNumber b = IntegerNumber.convertToInteger(decimal.substring(0, start));

            top = a.sub(b);

            StringBuilder nineSb = new StringBuilder();
            for(int i = 0, len = decimal.length() - start; i < len; i++){
                nineSb.append('9');
            }
            for(int i = 0, len = repetend + 1; i < len; i++){
                nineSb.append('0');
            }

            bottom = IntegerNumber.convertToInteger(nineSb.toString());

            IntegerNumber gcd = top.greatestCommonDivisor(bottom);
            if(gcd != IntegerNumber.ONE){
                top = top.div(gcd);
                bottom = bottom.div(gcd);
            }

            byte sign = negative ? NumberConstant.NEGATIVE : NumberConstant.POSITIVE;
            this.numerator = top.getSign() == sign ? top : top.abs();
            this.denominator = bottom.abs();
        }
    }

    public RationalNumber add(RationalNumber num){
        if(this == ZERO){
            return num;
        }else if(num == ZERO){
            return this;
        }else{
            IntegerNumber top;
            IntegerNumber bottom;

            if(this.denominator.compareTo(num.denominator) == 0){
                top = num.numerator.add(this.numerator);
                bottom = num.denominator;
            }else{
                bottom = this.denominator.mult(num.denominator);
                top = this.denominator.mult(num.numerator).add(num.denominator.mult(this.numerator));

                IntegerNumber gcd = bottom.greatestCommonDivisor(top);
                if(gcd != IntegerNumber.ONE){
                    top = top.div(gcd);
                    bottom = bottom.div(gcd);
                }
            }

            return new RationalNumber(top, bottom);
        }
    }

    public RationalNumber sub(RationalNumber num){
        return this.add(num.not());
    }

    public RationalNumber mult(RationalNumber num){
        if(this == ZERO || num == ZERO){ return ZERO; }

        if(this == ONE){
            return num;
        }else if(num == ONE){
            return this;
        }else{
            IntegerNumber top = this.numerator.mult(num.numerator);
            IntegerNumber bottom = this.denominator.mult(num.denominator);
            IntegerNumber gcd = top.greatestCommonDivisor(bottom);
            if(gcd != IntegerNumber.ONE){
                top = top.div(gcd);
                bottom = bottom.div(gcd);
            }
            return new RationalNumber(top, bottom);
        }
    }

    public RationalNumber div(RationalNumber num){
        return this.mult(num.upend());
    }

    private FactorObject factorObject = null;

    @Override
    protected FactorObject factorization() {
        if(this.factorObject == null){
            this.factorObject = new FactorObject(this, null);
        }
        return this.factorObject;
    }

    public RationalNumber not(){
        if(this == ONE){
            return N_ONE;
        }else if(this == N_ONE){
            return ONE;
        }else if(this == ZERO){
            return this;
        }else{
            return new RationalNumber(this.numerator.not(), this.denominator);
        }
    }

    private RationalNumber upend(){
        return new RationalNumber(this.denominator, this.numerator);
    }

    @Override
    public String toDecimal(int count) {
        IntegerNumber leftMove = this.numerator.decLeftMove(count + 1);
        IntegerNumber div = leftMove.div(this.denominator);
        String s = div.toString();
        int a = s.length() - count - 1;
        int last = s.charAt(s.length() - 1) - '0';
        if(last > 4){
            div = div.add(IntegerNumber.ONE.decLeftMove(1));
            s = div.toString();
        }
        s = s.substring(0, a) + '.' + s.substring(a, s.length() - 1);
        return s;
    }

    @Override
    public String toString() {
        return numerator.toString() + "/" + denominator.toString();
    }

    @Override
    public AbstractRealNumber add(AbstractRealNumber num) {
        if(num.getClass() == RationalNumber.class){
            return this.add((RationalNumber)num);
        }else {
            return PolynomialNumber.createPolynomial(num).add(this);
        }
    }

    @Override
    public AbstractRealNumber sub(AbstractRealNumber num) {
        return this.add(num.not());
    }

    @Override
    public AbstractRealNumber mult(AbstractRealNumber num) {
        if(num.getClass() == RationalNumber.class){
            return this.mult((RationalNumber)num);
        }else{
            return PolynomialNumber.createPolynomial(num).mult(this);
        }
    }

    @Override
    public AbstractRealNumber div(AbstractRealNumber num) {
        if(num.getClass() == RationalNumber.class){
            return this.mult((RationalNumber)num);
        }else{
            return PolynomialNumber.createPolynomial(num).mult(this);
        }
    }

    @Override
    public int compareTo(INumber o) {
        return 0;
    }
}
