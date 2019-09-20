package frog.calculator.math;

import frog.calculator.util.StringUtils;

/**
 * 有理数
 */
public class RationalNumber extends RealNumber {

    private IntegerNumber numerator = IntegerNumber.ZERO;    // 分子

    private IntegerNumber denominator = IntegerNumber.ONE;  // 分母

    protected RationalNumber(){}

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
        convertDecimal(decimal, pos);
    }

    private void convertDecimal(String decimal, int pos){
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
        if(StringUtils.isBlank(decimal)){
            throw new IllegalArgumentException("decimal is blank.");
        }

        int pos = decimal.indexOf(".");
        if(pos < 0){
            convertDecimal(decimal, pos);
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
        }
    }

    // 约分
    private void reduce(){
        IntegerNumber gcd = this.numerator.greatestCommonDivisor(denominator);
        if(gcd != IntegerNumber.ONE){
            this.numerator = this.numerator.div(gcd);
            this.denominator = this.denominator.div(gcd);
        }
    }

    @Override
    public INumber add(INumber number) {
        return null;
    }

    @Override
    public INumber sub(INumber number) {
        return null;
    }

    @Override
    public INumber mult(INumber number) {
        return null;
    }

    @Override
    public INumber div(INumber number) {
        return null;
    }
}
