package frog.calculator.math.number;

import frog.calculator.util.StringUtils;

/**
 * 有理数
 */
public final class RationalNumber extends AbstractBaseNumber implements Comparable<RationalNumber>{

    private final IntegerNumber numerator;    // 分子

    private final IntegerNumber denominator;  // 分母, 只可能是正数

    public static final RationalNumber ZERO = new RationalNumber(IntegerNumber.ZERO, IntegerNumber.ONE);

    public static final RationalNumber ONE = new RationalNumber(IntegerNumber.ONE, IntegerNumber.ONE);

    public static final RationalNumber N_ONE = new RationalNumber(IntegerNumber.ONE.not(), IntegerNumber.ONE);

    public RationalNumber(IntegerNumber numerator, IntegerNumber denominator){
        if(denominator == null){
            denominator = IntegerNumber.ONE;
        }
        this.denominator = denominator.abs();
        int sign = numerator.getSign() ^ denominator.getSign();
        this.numerator = sign == numerator.getSign() ? numerator : numerator.not();
    }

    public RationalNumber(IntegerNumber numerator) {
        this(numerator, null);
    }

    public RationalNumber(String numerator, String denominator){
        IntegerNumber top;
        IntegerNumber bottom;

        if(StringUtils.isNotBlank(numerator)){
            top = IntegerNumber.valueOf(numerator);
        }else{
            top = IntegerNumber.ZERO;
        }
        if(StringUtils.isNotBlank(denominator)){
            bottom = IntegerNumber.valueOf(denominator);
        }else{
            bottom = IntegerNumber.ONE;
        }

        IntegerNumber gcd = bottom.gcd(top);
        if(gcd != IntegerNumber.ONE){
            top = top.div(gcd);
            bottom = bottom.div(gcd);
        }

        int sign = top.getSign() ^ bottom.getSign();
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
            top = IntegerNumber.valueOf(decimal);
        }else{
            top = IntegerNumber.valueOf(decimal.replace(".", ""));

            int len = decimal.length() - pos - 1;
            StringBuilder denominator = new StringBuilder(len + 1);
            denominator.append("1");
            for(int i = 0; i < len; i++){
                denominator.append('0');
            }
            bottom = IntegerNumber.valueOf(denominator.toString());

            IntegerNumber gcd = bottom.gcd(top);
            if(gcd != IntegerNumber.ONE){
                top = top.div(gcd);
                bottom = bottom.div(gcd);
            }
        }
        int sign = top.getSign() ^ bottom.getSign();
        this.numerator = top.getSign() == sign ? top : top.not();
        this.denominator = bottom.abs();
    }

    /**
     * 无限循环小数 <br/>
     * 需要注意的是, repetend后面的部分会被认为是一个完整的循环节
     * @param decimal 小数
     * @param repetend 小数循环节开始的位置(从0计), repetend取int类型是合理的, 由于语言或者计算机的限制, repetend不会超过int范围
     */
    public RationalNumber(String decimal, int repetend){
        if(StringUtils.isBlank(decimal)){
            throw new IllegalArgumentException("decimal is blank.");
        }

        int pos = decimal.indexOf(".");
        if(pos < 0){
            this.numerator = IntegerNumber.valueOf(decimal);
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
            IntegerNumber a = IntegerNumber.valueOf(decimal);
            IntegerNumber b = IntegerNumber.valueOf(decimal.substring(0, start));

            top = a.sub(b);

            StringBuilder nineSb = new StringBuilder();
            for(int i = 0, len = decimal.length() - start; i < len; i++){
                nineSb.append('9');
            }
            for(int i = 0, len = repetend + 1; i < len; i++){
                nineSb.append('0');
            }

            bottom = IntegerNumber.valueOf(nineSb.toString());

            IntegerNumber gcd = top.gcd(bottom);
            if(gcd != IntegerNumber.ONE){
                top = top.div(gcd);
                bottom = bottom.div(gcd);
            }

            int sign = negative ? NumberConstant.SIGN_NEGATIVE : NumberConstant.SIGN_POSITIVE;
            this.numerator = top.getSign() == sign ? top : top.abs();
            this.denominator = bottom.abs();
        }
    }

    public RationalNumber add(RationalNumber num){
        if(this.equals(ZERO)){
            return num;
        }else if(num.equals(ZERO)){
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

                IntegerNumber gcd = bottom.gcd(top);
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
        if(this.equals(ZERO) || num.equals(ZERO)){ return ZERO; }

        if(this.equals(ONE)){
            return num;
        }else if(num.equals(ONE)){
            return this;
        }else{
            IntegerNumber top = this.numerator.mult(num.numerator);
            IntegerNumber bottom = this.denominator.mult(num.denominator);
            IntegerNumber gcd = top.gcd(bottom);
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

    public RationalNumber not(){
        if(this.equals(ONE)){
            return N_ONE;
        }else if(this.equals(N_ONE)){
            return ONE;
        }else if(this.equals(ZERO)){
            return ZERO;
        }else{
            return new RationalNumber(this.numerator.not(), this.denominator);
        }
    }

    /**
     * 颠倒分子分母
     */
    public RationalNumber upend(){
        return new RationalNumber(this.denominator, this.numerator);
    }

    public String toDecimal() {
        // TODO 保留小数位数, 舍入策略等
        if(IntegerNumber.ONE.equals(this.denominator)){
            return this.numerator.toString();
        }else{
            IntegerNumber.Remainder remainder = new IntegerNumber.Remainder();
            IntegerNumber quotient = this.numerator.div(this.denominator, remainder);
            IntegerNumber rem = remainder.getRemainder();
//            long bcount = (long) precision;
//            if(bcount > Integer.MAX_VALUE){
//                throw new IllegalArgumentException("precision is too large.");
//            }
            IntegerNumber remEx = rem.decLeftShift(this.scale);

            IntegerNumber decimal = remEx.div(this.denominator, remainder);
            String decimalStr = decimal.toString();
            if(decimalStr.length() < this.scale){
                decimalStr = StringUtils.leftFill(decimalStr, '0', this.scale - decimalStr.length());
            }
            if(IntegerNumber.ZERO.equals(remainder.getRemainder()) && decimalStr.endsWith("0")){
                decimalStr = StringUtils.rightTrim(decimalStr, '0');
            }
            return quotient.toString() + "." + decimalStr;
        }
    }

    @Override
    public String toString() {
        if(RESERVE_STRUCTURE == this.scale){
            if(denominator.equals(IntegerNumber.ONE)){
                return numerator.toString();
            }else{
                return numerator.toString() + "/" + denominator.toString();
            }
        }else{
            return this.toDecimal();
        }
    }

    @Override
    public int compareTo(RationalNumber o) {
        if(this == o) return 0;

        int sign = this.numerator.getSign();
        if(sign != o.numerator.getSign()){
            if(sign == NumberConstant.SIGN_POSITIVE){
                return 1;
            }else{
                return -1;
            }
        }else{
            int mark;
            if(this.denominator.equals(o.denominator)){
                mark = this.numerator.compareTo(o.numerator);
            }else{
                mark = this.numerator.mult(o.denominator).compareTo(o.numerator.mult(this.denominator));
            }
            if(sign == NumberConstant.SIGN_NEGATIVE){
                mark = -mark;
            }
            return mark;
        }
    }

    public static RationalNumber valueOf(String numString){
        int dot1 = numString.indexOf('.');
        int dot2 = numString.indexOf('_');
        RationalNumber rationalNumber;
        if(dot2 == -1){ // 没有循环节
            rationalNumber = new RationalNumber(numString);
        }else{  // 有循环节
            numString = numString.replace("_", "");
            rationalNumber = new RationalNumber(numString, dot2 - dot1 - 1);
        }
        return rationalNumber;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){ return true; }

        if(o == null || o.getClass() != RationalNumber.class){
            return false;
        }

        RationalNumber that = (RationalNumber) o;
        if(!that.numerator.equals(this.numerator)){
            return false;
        }else if((that.denominator == null) != (this.denominator == null)){
            return false;
        }else{
            return this.denominator == null || this.denominator.equals(that.denominator);
        }

    }

    /**
     * 转换为整数, 如果转换失败, 则返回null
     */
    public IntegerNumber toInteger() {
        if(this.denominator == null || this.denominator.equals(IntegerNumber.ONE)){
            return this.numerator;
        }
        return null;
    }
}
