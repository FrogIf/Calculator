package frog.calculator.math.INT;

public class IntegerNumber {

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

    public IntegerNumber(String number){
        number = fixNumber(number);
        if("0".equals(number) || "-0".equals(number)){
            this.number = ZERO_STR;
            this.literal = "0";
            return;
        }else if("1".equals(number)){
            this.number = ONE_STR;
            this.literal = "1";
            return;
        }

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

    private String fixNumber(String number){
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
            result.number = IntegerUtil.add(left.number, right.number);
            result.sign = left.sign;
        }else{
            int fa = IntegerUtil.compare(left.number, right.number);
            if(fa == 0){
                return ZERO;
            }else {
                result = new IntegerNumber();
                if(fa < 0){
                    result.number = IntegerUtil.subtract(right.number, left.number);
                    result.sign = NEGATIVE;
                }else{
                    result.number = IntegerUtil.subtract(left.number, right.number);
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
        StringBuilder resultSb = IntegerUtil.multiply(this.number, r.number);
        IntegerNumber result = new IntegerNumber();
        result.number = resultSb;
        result.sign = (byte) (1 & (this.sign ^ r.sign));
        return result;
    }

    public IntegerNumber divFloor(IntegerNumber r){
        return null;
    }

    /**
     * 求最大公约数
     * 返回值大于0
     * @param num
     * @return
     */
    public IntegerNumber greatestCommonDivisor(IntegerNumber num){
        StringBuilder gcd = IntegerUtil.gcd(num.number, this.number);
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


}
