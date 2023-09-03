package sch.frog.calculator.number.impl;

import sch.frog.calculator.number.util.StrUtils;

/**
 * 有理数
 */
public final class RationalNumber extends AbstractBaseNumber implements Comparable<RationalNumber>{

    private final IntegerNumber numerator;    // 分子, 正负号记录在分子上

    private final IntegerNumber denominator;  // 分母, 只可能是正数

    public static final RationalNumber ZERO = new RationalNumber(IntegerNumber.ZERO, IntegerNumber.ONE);

    public static final RationalNumber ONE = new RationalNumber(IntegerNumber.ONE, IntegerNumber.ONE);

    public static final RationalNumber N_ONE = new RationalNumber(IntegerNumber.ONE.not(), IntegerNumber.ONE);

    public RationalNumber(IntegerNumber numerator, IntegerNumber denominator){
        if(denominator == null){
            denominator = IntegerNumber.ONE;
        }
        this.denominator = denominator.abs();
        NumberSign sign = numerator.getSign() == denominator.getSign() ? NumberSign.POSITIVE : NumberSign.NEGATIVE;
        this.numerator = sign == numerator.getSign() ? numerator : numerator.not();
    }

    public RationalNumber(IntegerNumber numerator) {
        this(numerator, null);
    }

    public RationalNumber(String numerator, String denominator){
        IntegerNumber top;
        IntegerNumber bottom;

        if(StrUtils.isNotBlank(numerator)){
            top = IntegerNumber.valueOf(numerator);
        }else{
            top = IntegerNumber.ZERO;
        }
        if(StrUtils.isNotBlank(denominator)){
            bottom = IntegerNumber.valueOf(denominator);
        }else{
            bottom = IntegerNumber.ONE;
        }

        IntegerNumber gcd = bottom.gcd(top);
        if(!gcd.equals(IntegerNumber.ONE)){
            top = top.div(gcd);
            bottom = bottom.div(gcd);
        }

        NumberSign sign = top.getSign() == bottom.getSign() ? NumberSign.POSITIVE : NumberSign.NEGATIVE;
        this.numerator = top.getSign() == sign ? top : top.not();
        this.denominator = bottom.abs();
    }

    public RationalNumber(String decimal){
        if(StrUtils.isBlank(decimal)){
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
            StringBuilder denominatorBuilder = new StringBuilder(len + 1);
            denominatorBuilder.append("1");
            for(int i = 0; i < len; i++){
                denominatorBuilder.append('0');
            }
            bottom = IntegerNumber.valueOf(denominatorBuilder.toString());

            IntegerNumber gcd = bottom.gcd(top);
            if(!gcd.equals(IntegerNumber.ONE)){
                top = top.div(gcd);
                bottom = bottom.div(gcd);
            }
        }
        NumberSign sign = top.getSign() == bottom.getSign() ? NumberSign.POSITIVE : NumberSign.NEGATIVE;
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
        if(StrUtils.isBlank(decimal)){
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
            if(!gcd.equals(IntegerNumber.ONE)){
                top = top.div(gcd);
                bottom = bottom.div(gcd);
            }

            NumberSign sign = negative ? NumberSign.NEGATIVE : NumberSign.POSITIVE;
            this.numerator = top.getSign() == sign ? top : top.abs();
            this.denominator = bottom.abs();
        }
    }

    /**
     * 加法
     */
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
                return new RationalNumber(top, bottom);
            }else{
                bottom = this.denominator.mult(num.denominator);
                top = this.denominator.mult(num.numerator).add(num.denominator.mult(this.numerator));
                return reduction(top, bottom);
            }

        }
    }

    /**
     * 减法
     */
    public RationalNumber sub(RationalNumber num){
        return this.add(num.not());
    }

    /**
     * 乘法
     */
    public RationalNumber mult(RationalNumber num){
        if(this.equals(ZERO) || num.equals(ZERO)){ return ZERO; }

        if(this.equals(ONE)){
            return num;
        }else if(num.equals(ONE)){
            return this;
        }else{
            IntegerNumber top = this.numerator.mult(num.numerator);
            IntegerNumber bottom = this.denominator.mult(num.denominator);
            return reduction(top, bottom);
        }
    }

    /**
     * 乘法
     */
    public RationalNumber mult(IntegerNumber num){
        IntegerNumber top = this.numerator.mult(num);
        return reduction(top, this.denominator);
    }

    /**
     * 除法
     */
    public RationalNumber div(RationalNumber num){
        return this.mult(num.upend());
    }

    /**
     * 除法
     */
    public RationalNumber div(IntegerNumber num){
        NumberSign sign = num.getSign();
        IntegerNumber top = this.numerator;
        if(sign == NumberSign.NEGATIVE){
            top = top.not();
            num = num.abs();
        }
        IntegerNumber bottom = this.denominator.mult(num);
        return reduction(top, bottom);
    }

    /**
     * 约分
     */
    private static RationalNumber reduction(IntegerNumber top, IntegerNumber bottom){
        IntegerNumber gcd = top.gcd(bottom);
        if(!gcd.equals(IntegerNumber.ONE)){
            return new RationalNumber(top.div(gcd), bottom.div(gcd));
        }else{
            return new RationalNumber(top, bottom);
        }
    }

    /**
     * 取反
     */
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

    /**
     * 绝对值
     */
    public RationalNumber abs(){
        if(this.numerator.getSign() == NumberSign.POSITIVE){
            return this;
        }else{
            return new RationalNumber(this.numerator.not(), this.denominator);
        }
    }

    @Override
    public int compareTo(RationalNumber o) {
        if(this == o) return 0;

        NumberSign sign = this.numerator.getSign();
        if(sign != o.numerator.getSign()){
            if(sign == NumberSign.POSITIVE){
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
            if(sign == NumberSign.NEGATIVE){
                mark = -mark;
            }
            return mark;
        }
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

    @Override
    public int hashCode(){
        return this.numerator.hashCode() + this.denominator.hashCode();
    }

    public static RationalNumber valueOf(String numString){
        int dot1 = numString.indexOf('.');
        int dot2 = numString.indexOf('_');
        int ePos = numString.indexOf(NumberConstant.SCIENTIFIC_MARK);

        String beforeE;
        int eVal = 0;
        if(ePos > 0){
            beforeE = numString.substring(0, ePos);
            eVal = Integer.parseInt(numString.substring(ePos + 1));
        }else{
            beforeE = numString;
        }

        RationalNumber rationalNumber;
        if(dot2 == -1){ // 没有循环节
            rationalNumber = new RationalNumber(beforeE);
        }else{  // 有循环节
            beforeE = beforeE.replace("_", "");
            rationalNumber = new RationalNumber(beforeE, dot2 - dot1 - 1);
        }

        // 存在科学计数法
        if(eVal > 0){
            IntegerNumber newNumerator = rationalNumber.numerator.decLeftShift(eVal);
            rationalNumber = reduction(newNumerator, rationalNumber.denominator);
        }else if(eVal < 0){
            IntegerNumber newDenominator = rationalNumber.denominator.decLeftShift(-eVal);
            rationalNumber = reduction(rationalNumber.numerator, newDenominator);
        }

        return rationalNumber;
    }

    public static RationalNumber valueOf(double value){
        return valueOf(Double.toString(value));
    }

    @Override
    public String decimal(int scale, NumberRoundingMode roundingMode) {
        if(IntegerNumber.ONE.equals(this.denominator)){
            return this.numerator.decimal(scale, roundingMode);
        }else{
            int c = this.numerator.decBits() - this.denominator.decBits();
            if(c > NumberConstant.SCIENTIFIC_THRESHOLD || c < -NumberConstant.SCIENTIFIC_THRESHOLD){ // 采用科学计数法
                return this.scientificNotation(scale, roundingMode);
            }else{
                return this.toPlainString(scale, roundingMode);
            }
        }
    }

    /**
     * 以小数形式输出
     * @param scale 保留小数位数
     * @param roundingMode 舍入模式
     * @return 小数字符串
     */
    public String toPlainString(int scale, NumberRoundingMode roundingMode){
        IntegerNumber.Remainder remainder = new IntegerNumber.Remainder();
        IntegerNumber quotient = this.numerator.div(this.denominator, remainder);

        StringBuilder number = new StringBuilder();
        number.append(quotient.toPlainString());
        int needLen = scale + roundingMode.extraDecBitCount();
        if(needLen > 0){
            IntegerNumber r = remainder.getValue();
            IntegerNumber decLeftShift = r.decLeftShift(needLen);
            IntegerNumber dq = decLeftShift.div(this.denominator, remainder);
    
            String dqStr = dq.toPlainString();
            if(dqStr.length() < needLen){
                dqStr = StrUtils.leftFill(dqStr, '0', needLen - dqStr.length());
            }

            number.append('.').append(dqStr);
            if(remainder.getValue().compareTo(0) > 0){
                number.append('1');
            }
        }else if(remainder.getValue().compareTo(0) > 0){
            number.append(".1");
        }
        return NumberRoundingMode.roundOff(number.toString(), scale, roundingMode);
    }

    @Override
    public String toString() {
        if(denominator.equals(IntegerNumber.ONE)){
            return numerator.toString();
        }else{
            return numerator.toString() + "/" + denominator.toString();
        }
    }

    @Override
    public String scientificNotation(int scale, NumberRoundingMode roundingMode) {
        if(IntegerNumber.ONE.equals(this.denominator)){
            return this.numerator.scientificNotation(scale, roundingMode);
        }else if(IntegerNumber.ZERO.equals(this.numerator)){
            return NumberRoundingMode.roundOff("0", scale, roundingMode);
        }else {
            IntegerNumber.Remainder remainder = new IntegerNumber.Remainder();
            int needLen = scale + roundingMode.extraDecBitCount(); // 需要的小数位数

            int decBitInv = this.numerator.decBits() - this.denominator.decBits();
            int enlarge = needLen - decBitInv + 1; // 放大的倍数
            if(enlarge < 0){
                enlarge = 0;
            }

            IntegerNumber newNumerator = this.numerator.decLeftShift(enlarge);

            int start = newNumerator.getSign() == NumberSign.NEGATIVE ? 1 : 0;
            IntegerNumber quotient = newNumerator.div(this.denominator, remainder);
            String qStr = quotient.toPlainString();

            String afterDot = qStr.substring(start + 1);
            String number = qStr.substring(0, start + 1) + "." + afterDot;

            int n = -(enlarge - afterDot.length());

            // 判断是否有余数, 如果有, 需要在后面再加1, 使得舍入时, 能感知到有数据丢弃
            if(remainder.getValue().compareTo(0) > 0){
                number = number + "1";
            }

            return InnerNumberUtil.scientificNotationTransfer(number, scale, roundingMode, n);
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

    @Override
    public NumberSign getSign() {
        return this.numerator.getSign();
    }

    /**
     * 分子
     */
    public IntegerNumber getNumerator() {
        return numerator;
    }

    /**
     * 分母
     */
    public IntegerNumber getDenominator() {
        return denominator;
    }
}
