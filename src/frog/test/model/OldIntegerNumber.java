package frog.test.model;

import frog.calculator.math.INumber;
import frog.calculator.math.rational.IntegerNumber;

/**
 * 整数
 */
public final class OldIntegerNumber implements INumber, Comparable<OldIntegerNumber> {

    static final StringBuilder ZERO_STR = new StringBuilder("0");

    static final StringBuilder ONE_STR = new StringBuilder("1");

    private final byte sign;

    private final StringBuilder number;   // 最低位在前, 最高位在后

    private String literal;

    private OldIntegerNumber(StringBuilder number, byte sign){
        this.number = number;
        this.sign = sign;
    }

    public static final OldIntegerNumber ZERO = new OldIntegerNumber(ZERO_STR, IntegerNumber.POSITIVE);

    public static final OldIntegerNumber ONE = new OldIntegerNumber(ONE_STR, IntegerNumber.POSITIVE);

    private OldIntegerNumber(String number){
        this.sign = number.charAt(0) != '-' ? IntegerNumber.POSITIVE : IntegerNumber.NEGATIVE;

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

        number = number.substring(this.sign + zero);
        if(number.length() == 0){
            number = "0";
        }
        this.number = new StringBuilder(number).reverse();
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

    private static OldIntegerNumber add(OldIntegerNumber left, OldIntegerNumber right, byte operator){
        OldIntegerNumber result;

        if((left.sign ^ right.sign) == operator){
            StringBuilder number = OldPositiveIntegerUtil.add(left.number, right.number);
            result = new OldIntegerNumber(number, left.sign);
        }else{
            int fa = OldPositiveIntegerUtil.compare(left.number, right.number);
            if(fa == 0){
                return ZERO;
            }else {
                StringBuilder number;
                byte sign = IntegerNumber.POSITIVE;
                if(fa < 0){
                    number = OldPositiveIntegerUtil.subtract(right.number, left.number);
                    sign = IntegerNumber.NEGATIVE;
                }else{
                    number = OldPositiveIntegerUtil.subtract(left.number, right.number);
                }
                result = new OldIntegerNumber(number, (byte) (left.sign ^ sign));
            }
        }

        return result;
    }

    public OldIntegerNumber add(OldIntegerNumber r){
        return add(this, r, IntegerNumber.POSITIVE);
    }

    public OldIntegerNumber sub(OldIntegerNumber r){
        return add(this, r, IntegerNumber.NEGATIVE);
    }

    public OldIntegerNumber mult(OldIntegerNumber r){
        return new OldIntegerNumber(OldPositiveIntegerUtil.multiply(this.number, r.number), (byte) (this.sign ^ r.sign));
    }

    public OldIntegerNumber div(OldIntegerNumber r){
        return new OldIntegerNumber(OldPositiveIntegerUtil.division(this.number, r.number), (byte) (this.sign ^ r.sign));
    }

    /**
     * 求最大公约数
     * 返回值大于0
     * @param num
     * @return
     */
    public OldIntegerNumber greatestCommonDivisor(OldIntegerNumber num){
        return new OldIntegerNumber(OldPositiveIntegerUtil.gcd(num.number, this.number), IntegerNumber.POSITIVE);
    }

    public OldIntegerNumber not(){
        return new OldIntegerNumber(this.number, (byte) (1 ^ this.sign));
    }

    @Override
    public String toDecimal(int count) {
        return null;
    }

    public OldIntegerNumber abs() {
        if(this.sign == IntegerNumber.NEGATIVE){
            return new OldIntegerNumber(this.number, IntegerNumber.POSITIVE);
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
        return OldPositiveIntegerUtil.isOdd(this.number);
    }

    @Override
    public String toString() {
        if(this.literal == null){
            StringBuilder temp = new StringBuilder(this.number);
            if(this.sign == IntegerNumber.NEGATIVE){
                temp.append('-');
            }
            this.literal = temp.reverse().toString();
        }
        if(this.literal.replaceAll("0", "").equals("")){
            this.literal = "0";
        }
        return this.literal;
    }

    /**
     * 绝对值比较大小
     */
    public int absCompareTo(OldIntegerNumber o){
        return OldPositiveIntegerUtil.compare(this.number, o.number);
    }

    public byte getSign() {
        return sign;
    }

    @Override
    public int compareTo(OldIntegerNumber o) {
        if(this.sign == o.sign){
            if(this.sign == IntegerNumber.POSITIVE){
                return OldPositiveIntegerUtil.compare(this.number, o.number);
            }else {
                return -OldPositiveIntegerUtil.compare(this.number, o.number);
            }
        }else{
            if(this.sign == IntegerNumber.POSITIVE){
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

        OldIntegerNumber that = (OldIntegerNumber) o;

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

    public static OldIntegerNumber valueOf(String number){
        number = fixNumber(number);
        if("0".equals(number) || "-0".equals(number)){
            return ZERO;
        }else if("1".equals(number)){
            return ONE;
        }else{
            return new OldIntegerNumber(number);
        }
    }

    public static OldIntegerNumber valueOf(int num){
        if(num == 0){
            return ZERO;
        }else if(num == 1){
            return ONE;
        }else{
            return new OldIntegerNumber(String.valueOf(num));
        }
    }
}
