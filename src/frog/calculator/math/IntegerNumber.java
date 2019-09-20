package frog.calculator.math;

/**
 * 整数
 */
public final class IntegerNumber extends RationalNumber{

    private static final byte POSITIVE = 0;

    private static final byte NEGATIVE = 1;

    public static final IntegerNumber ZERO = new IntegerNumber();

    public static final IntegerNumber ONE = new IntegerNumber();

    static final StringBuilder ZERO_STR = new StringBuilder("0");

    static final StringBuilder ONE_STR = new StringBuilder("1");

    static {
        ZERO.number = ZERO_STR;
        ONE.number = ONE_STR;
    }

    private byte sign;

    private StringBuilder number;   // 最低位在前

    private String literal;

    private IntegerNumber(){ }


    public static IntegerNumber convertToInteger(String number){
        number = fixNumber(number);
        if("0".equals(number) || "-0".equals(number)){
            return ZERO;
        }else if("1".equals(number)){
            return ONE;
        }else{
            return new IntegerNumber(number);
        }
    }

    private IntegerNumber(String number){
        this.sign = number.charAt(0) != '-' ? POSITIVE : NEGATIVE;

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

        this.literal = number.substring(this.sign + zero);
        this.number = new StringBuilder(this.literal).reverse();
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

    private IntegerNumber add(IntegerNumber left, IntegerNumber right, byte operator){
        IntegerNumber result;

        if((left.sign ^ right.sign) == operator){
            result = new IntegerNumber();
            result.number = PositiveIntegerUtil.add(left.number, right.number);
            result.sign = left.sign;
        }else{
            int fa = PositiveIntegerUtil.compare(left.number, right.number);
            if(fa == 0){
                return ZERO;
            }else {
                result = new IntegerNumber();
                if(fa < 0){
                    result.number = PositiveIntegerUtil.subtract(right.number, left.number);
                    result.sign = NEGATIVE;
                }else{
                    result.number = PositiveIntegerUtil.subtract(left.number, right.number);
                }
            }
            result.sign = (byte) (left.sign ^ result.sign);
        }

        return result;
    }

    public IntegerNumber add(IntegerNumber r){
        return add(this, r, POSITIVE);
    }

    public IntegerNumber sub(IntegerNumber r){
        return add(this, r, NEGATIVE);
    }

    public IntegerNumber mult(IntegerNumber r){
        StringBuilder resultSb = PositiveIntegerUtil.multiply(this.number, r.number);
        IntegerNumber result = new IntegerNumber();
        result.number = resultSb;
        result.sign = (byte) (1 & (this.sign ^ r.sign));
        return result;
    }

    public IntegerNumber div(IntegerNumber r){
        IntegerNumber result = new IntegerNumber();
        result.number = PositiveIntegerUtil.division(this.number, r.number);
        result.sign = (byte) (1 & (this.sign ^ r.sign));
        return result;
    }

    /**
     * 求最大公约数
     * 返回值大于0
     * @param num
     * @return
     */
    public IntegerNumber greatestCommonDivisor(IntegerNumber num){
        StringBuilder gcd = PositiveIntegerUtil.gcd(num.number, this.number);
        IntegerNumber result = new IntegerNumber();
        result.number = gcd;
        return result;
    }

    @Override
    public String toString() {
        if(this.literal == null){
            StringBuilder temp = new StringBuilder(this.number);
            if(this.sign == NEGATIVE){
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

//    @Override
//    public int compareTo(IntegerNumber o) {
//        if(this.sign == o.sign){
//            if(this.sign == POSITIVE){
//                return PositiveIntegerUtil.compare(this.number, o.number);
//            }else {
//                return -PositiveIntegerUtil.compare(this.number, o.number);
//            }
//        }else{
//            if(this.sign == POSITIVE){
//                return 1;
//            }else{
//                return -1;
//            }
//        }
//    }
}
