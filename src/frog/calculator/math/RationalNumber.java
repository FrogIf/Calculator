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
            /*
             * 循环小数转分数:
             * 分子 = (b + a * (10 ^ m - 1))
             * 分母 = ((10 ^ m - 1) * 10 ^ n)
             * a -- 小数的不循环部分
             * b -- 小数的循环部分
             * n -- 不循环部分的位数
             * m -- 循环部分的位数
             */
            if(repetend == 0){  // 纯循环小数 n = 0, a = 0 --> b / (10 ^ m - 1)

            }else{  // 混循环小数

            }
            this.numerator = null;
            this.denominator = null;
            this.sign = 1;
        }
    }

    public RationalNumber add(RationalNumber number){
        IntegerNumber top;
        IntegerNumber bottom;

        if(this.compareTo(number) == 0){
            top = number.numerator.add(this.numerator);
            bottom = number.denominator;
        }else{
            bottom = this.denominator.mult(number.denominator);
            top = this.denominator.mult(number.numerator).add(number.denominator.mult(this.numerator));

            IntegerNumber gcd = bottom.greatestCommonDivisor(top);
            if(gcd != IntegerNumber.ONE){
                top = top.div(gcd);
                bottom = bottom.div(gcd);
            }
        }

        return new RationalNumber(top, bottom);
    }

    public RationalNumber sub(RationalNumber number){
        return null;
    }

    public RationalNumber mult(RationalNumber number){
        return null;
    }

    public RationalNumber div(RationalNumber number){
        return null;
    }
}
