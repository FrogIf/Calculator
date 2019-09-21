package frog.calculator.math;

import frog.calculator.util.StringUtils;

/**
 * 有理数
 */
public class RationalNumber extends RealNumber {

    private final IntegerNumber numerator;    // 分子

    private final IntegerNumber denominator;  // 分母

    private final byte sign;

    private RationalNumber(IntegerNumber numerator, IntegerNumber denominator){
        this.numerator = numerator;
        this.denominator = denominator;
        this.sign = (byte) (1 & (numerator.getSign() ^ denominator.getSign()));
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

        this.numerator = top;
        this.denominator = bottom;
        this.sign = (byte) (1 & (top.getSign() ^ bottom.getSign()));
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
        this.numerator = top;
        this.denominator = bottom;
        this.sign = (byte) (1 & (top.getSign() ^ bottom.getSign()));
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
            this.sign = this.numerator.getSign();
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

            this.numerator = top;
            this.denominator = bottom;
            this.sign = negative ? IntegerNumber.NEGATIVE : IntegerNumber.POSITIVE;
        }
    }

    public RationalNumber not(){
        return new RationalNumber(this.numerator.not(), this.denominator);
    }

    public RationalNumber upend(){
        return new RationalNumber(this.denominator, this.numerator);
    }

    public RationalNumber add(RationalNumber num){
        IntegerNumber top;
        IntegerNumber bottom;

        if(this.compareTo(num) == 0){
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

    public RationalNumber sub(RationalNumber num){
        return this.add(num.not());
    }

    public RationalNumber mult(RationalNumber num){
        IntegerNumber top = this.numerator.mult(num.numerator);
        IntegerNumber bottom = this.denominator.mult(num.denominator);
        IntegerNumber gcd = top.greatestCommonDivisor(bottom);
        if(gcd != IntegerNumber.ONE){
            top = top.div(gcd);
            bottom = bottom.div(gcd);
        }
        return new RationalNumber(top, bottom);
    }

    public RationalNumber div(RationalNumber number){
        return this.mult(number.upend());
    }

    @Override
    public byte getSign() {
        return sign;
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
        return (this.sign == IntegerNumber.NEGATIVE ? "-" : "") + numerator.toString() + "/" + denominator.toString();
    }
}
