package frog.calculator.math;

import frog.calculator.math.INT.IntegerNumber;
import frog.calculator.util.StringUtils;

/**
 * 有理数
 */
public class RationalNumber {

    private IntegerNumber numerator = IntegerNumber.ZERO;    // 分子

    private IntegerNumber denominator = IntegerNumber.ONE;  // 分母

    public RationalNumber(String numerator, String denominator){
        if(StringUtils.isNotBlank(numerator)){
            this.numerator = IntegerNumber.convertToInteger(numerator);
        }
        if(StringUtils.isNotBlank(denominator)){
            this.numerator = IntegerNumber.convertToInteger(denominator);
        }
        this.reduce();
    }

    public RationalNumber(String decimal){
        if(StringUtils.isBlank(decimal)){
            throw new IllegalArgumentException("decimal is blank.");
        }
        int pos = decimal.indexOf(".");
        if(pos < 0){
            this.numerator = IntegerNumber.convertToInteger(decimal);
        }else{
            this.numerator = IntegerNumber.convertToInteger(decimal.replace(".", ""));

            int len = decimal.length() - pos - 1;
            StringBuilder denominator = new StringBuilder(len + 1);
            denominator.append("1");
            for(int i = 0; i < len; i++){
                denominator.append('0');
            }
            this.denominator = IntegerNumber.convertToInteger(denominator.toString());
            this.reduce();
        }
    }

    /**
     * 无限循环小数 <br/>
     * 需要注意的是, repetend后面的部分会被认为是一个完整的循环节
     * @param decimal 小数
     * @param repetend 小数循环节开始的位置(从0计)
     */
    public RationalNumber(String decimal, int repetend){

    }

    public RationalNumber add(RationalNumber r){
        return null;
    }

    public RationalNumber sub(RationalNumber r){
        return null;
    }

    public RationalNumber mult(RationalNumber r){
        return null;
    }

    public RationalNumber div(RationalNumber r){
        return null;
    }

    // 约分
    private void reduce(){
        IntegerNumber gcd = this.numerator.greatestCommonDivisor(denominator);
        if(gcd != IntegerNumber.ONE){
            this.numerator = this.numerator.div(gcd);
            this.denominator = this.denominator.div(gcd);
        }
    }

}
