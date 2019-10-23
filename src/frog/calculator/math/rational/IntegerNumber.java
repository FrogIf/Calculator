package frog.calculator.math.rational;

import frog.calculator.math.INumber;
import frog.calculator.math.NumberConstant;

/**
 * 整数
 */
public final class IntegerNumber implements INumber, Comparable<IntegerNumber> {

    static final StringBuilder ZERO_STR = new StringBuilder("0");

    static final StringBuilder ONE_STR = new StringBuilder("1");

    private final byte sign;

    private final StringBuilder number;   // 最低位在前, 最高位在后

    private String literal;

    private IntegerNumber(StringBuilder number, byte sign){
        this.number = number;
        this.sign = sign;
    }

    public static final IntegerNumber ZERO = new IntegerNumber(ZERO_STR, NumberConstant.POSITIVE);

    public static final IntegerNumber ONE = new IntegerNumber(ONE_STR, NumberConstant.POSITIVE);

    private IntegerNumber(String number){
        this.sign = number.charAt(0) != '-' ? NumberConstant.POSITIVE : NumberConstant.NEGATIVE;

        int len = number.length();
        boolean pz = true;  // 是否存在前置0
        int zero = 0;
        for(int i = this.sign; i < len; i++){
            char ch = number.charAt(i);
            if(pz && ch == '0'){
                zero++;
                continue;
            }
            pz = false;
            if(ch < '0' || ch > '9'){
                throw new IllegalArgumentException("it's not a integer : " + number);
            }
        }

        this.number = new StringBuilder(number.substring(this.sign + zero)).reverse();
    }

    private static String fixNumber(String number){
        if(number == null){
            throw new IllegalArgumentException("input number is blank.");
        }else{
            number = number.trim();
            if(number.equals("") || number.equals("-")){
                throw new IllegalArgumentException("input number is blank.");
            }
        }
        return number;
    }

    private static IntegerNumber add(IntegerNumber left, IntegerNumber right, byte operator){
        IntegerNumber result;

        if((left.sign ^ right.sign) == operator){
            StringBuilder number = PositiveIntegerUtil.add(left.number, right.number);
            result = new IntegerNumber(number, left.sign);
        }else{
            int fa = PositiveIntegerUtil.compare(left.number, right.number);
            if(fa == 0){
                return ZERO;
            }else {
                StringBuilder number;
                byte sign = NumberConstant.POSITIVE;
                if(fa < 0){
                    number = PositiveIntegerUtil.subtract(right.number, left.number);
                    sign = NumberConstant.NEGATIVE;
                }else{
                    number = PositiveIntegerUtil.subtract(left.number, right.number);
                }
                result = new IntegerNumber(number, (byte) (left.sign ^ sign));
            }
        }

        return result;
    }

    public IntegerNumber add(IntegerNumber r){
        return add(this, r, NumberConstant.POSITIVE);
    }

    public IntegerNumber sub(IntegerNumber r){
        return add(this, r, NumberConstant.NEGATIVE);
    }

    public IntegerNumber mult(IntegerNumber r){
        return new IntegerNumber(PositiveIntegerUtil.multiply(this.number, r.number), (byte) (this.sign ^ r.sign));
    }

    public IntegerNumber div(IntegerNumber r){
        return new IntegerNumber(PositiveIntegerUtil.division(this.number, r.number), (byte) (this.sign ^ r.sign));
    }

    /**
     * 求最大公约数
     * 返回值大于0
     * @param num
     * @return
     */
    public IntegerNumber greatestCommonDivisor(IntegerNumber num){
        return new IntegerNumber(PositiveIntegerUtil.gcd(num.number, this.number), NumberConstant.POSITIVE);
    }

    public IntegerNumber not(){
        return new IntegerNumber(this.number, (byte) (1 ^ this.sign));
    }

    @Override
    public String toDecimal(int count) {
        return null;
    }

    public IntegerNumber abs() {
        if(this.sign == NumberConstant.NEGATIVE){
            return new IntegerNumber(this.number, NumberConstant.POSITIVE);
        }else{
            return this;
        }
    }
//    public IntegerNumber decLeftMove(int b){
//        StringBuilder n = new StringBuilder(this.number).reverse();
//        for(int i = 0; i < b; i++){
//            n.append('0');
//        }
//        n.reverse();
//        return new IntegerNumber(n, this.sign);
//    }

    public boolean isOdd(){
        return PositiveIntegerUtil.isOdd(this.number);
    }

    @Override
    public String toString() {
        if(this.literal == null){
            StringBuilder temp = new StringBuilder(this.number);
            if(this.sign == NumberConstant.NEGATIVE){
                temp.append('-');
            }
            this.literal = temp.reverse().toString();
        }
        return this.literal;
    }

    /**
     * 绝对值比较大小
     */
    public int absCompareTo(IntegerNumber o){
        return PositiveIntegerUtil.compare(this.number, o.number);
    }

    public byte getSign() {
        return sign;
    }

    @Override
    public int compareTo(IntegerNumber o) {
        if(this.sign == o.sign){
            if(this.sign == NumberConstant.POSITIVE){
                return PositiveIntegerUtil.compare(this.number, o.number);
            }else {
                return -PositiveIntegerUtil.compare(this.number, o.number);
            }
        }else{
            if(this.sign == NumberConstant.POSITIVE){
                return 1;
            }else{
                return -1;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IntegerNumber that = (IntegerNumber) o;

        if(that.number.length() == this.number.length()){
            for(int i = that.number.length() - 1; i >= 0; i--){
                if(that.number.charAt(i) != this.number.charAt(i)){
                    return false;
                }
            }
        }else{
            return false;
        }

        return true;
    }

    public static IntegerNumber valueOf(String number){
        number = fixNumber(number);
        if("0".equals(number) || "-0".equals(number)){
            return ZERO;
        }else if("1".equals(number)){
            return ONE;
        }else{
            return new IntegerNumber(number);
        }
    }

    public static IntegerNumber valueOf(int num){
        if(num == 0){
            return ZERO;
        }else if(num == 1){
            return ONE;
        }else{
            return new IntegerNumber(String.valueOf(num));
        }
    }
}
