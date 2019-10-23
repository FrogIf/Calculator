package frog.calculator.math.rational;

import frog.calculator.math.INumber;
import frog.calculator.math.NumberConstant;

public class NewIntegerNumber implements INumber {

    private static final int[] ZERO_VALUES = new int[1];

    private static final int[] ONE_VALUES = new int[]{1};

    public static final NewIntegerNumber ZERO = new NewIntegerNumber(NumberConstant.POSITIVE, ZERO_VALUES);

    public static final NewIntegerNumber ONE = new NewIntegerNumber(NumberConstant.POSITIVE, ONE_VALUES);

    private final byte sign;

    /*
     * 用于存储number的真实值, 可读数字中每9个数作为一个元素存入该数组中, 低位存储于低索引处, 高位存储于高索引处(little-endian)
     */
    private final int[] values;

    public NewIntegerNumber(byte sign, String literal) {
        this.sign = sign;
        this.literal = new String[1];
        this.literal[0] = literal;
        int len = literal.length();
        int vl = len / PositiveIntegerArrUtil.SINGLE_ELEMENT_LEN + (len % PositiveIntegerArrUtil.SINGLE_ELEMENT_LEN == 0 ? 0 : 1);
        this.values = new int[vl];

        int temp = 0;
        int step = 0;
        int move = 1;
        int j = 0;
        for(int i = len - 1; i >= 0; i--){
            temp += (literal.charAt(i) - '0') * move;
            move *= 10;
            step++;
            if(step % PositiveIntegerArrUtil.SINGLE_ELEMENT_LEN == 0){
                values[j++] = temp;
                step = 0;
                move = 1;
                temp = 0;
            }
        }
        if(temp > 0){
            values[j] = temp;
        }
    }

    private NewIntegerNumber(byte sign, int[] values){
        if(values == null || values.length == 0){
            throw new IllegalArgumentException("empty number.");
        }
        // fix values
        int i = values.length - 1;
        for(; i > 0; i--){
            if(values[i] > 0){
                break;
            }
        }
        if(i != values.length - 1){
            int[] nv = new int[i + 1];
            for(; i > -1; i--){
                nv[i] = values[i];
            }
            values = nv;
        }

        this.values = values;
        this.sign = sign;
    }

    /**
     * 对两个数进行相加/减
     * @param left 运算左侧数
     * @param right 运算右侧数
     * @param operator 指定运算类型, 0 - 相加, 1 - 相减
     */
    private static NewIntegerNumber accumulation(NewIntegerNumber left, NewIntegerNumber right, byte operator){
        NewIntegerNumber result;
        if((left.sign ^ right.sign) == operator){
            int[] values = PositiveIntegerArrUtil.add(left.values, right.values);
            result = new NewIntegerNumber(left.sign, values);
        }else{
            int c = PositiveIntegerArrUtil.compare(left.values, right.values);
            if(c == 0){
                result = ZERO;
            }else{
                int[] values;
                byte sign = NumberConstant.POSITIVE;
                if(c < 0){
                    values = PositiveIntegerArrUtil.subtract(right.values, left.values);
                    sign = NumberConstant.NEGATIVE;
                }else{
                    values = PositiveIntegerArrUtil.subtract(left.values, right.values);
                }
                result = new NewIntegerNumber((byte)(left.sign ^ sign), values);
            }
        }
        return result;
    }

    public NewIntegerNumber add(NewIntegerNumber num){
        return accumulation(this, num, NumberConstant.POSITIVE);
    }

    public NewIntegerNumber sub(NewIntegerNumber num){
        return accumulation(this, num, NumberConstant.NEGATIVE);
    }

    public NewIntegerNumber mult(NewIntegerNumber num){
        return new NewIntegerNumber((byte) (this.sign ^ num.sign), PositiveIntegerArrUtil.multiply(this.values, num.values));
    }

    public NewIntegerNumber div(NewIntegerNumber num, Remainder remainder){
        int[][] result = PositiveIntegerArrUtil.division(this.values, num.values);
        if(remainder != null){
            remainder.remainder = new NewIntegerNumber(NumberConstant.POSITIVE, result[1]);
        }
        return new NewIntegerNumber((byte) (this.sign ^ num.sign), result[0]);
    }

    @Override
    public NewIntegerNumber not() {
        return new NewIntegerNumber((byte) (1 ^ this.sign), this.values);
    }

    public NewIntegerNumber gcd(){
        return null;
    }

    public boolean isOdd(){
        return PositiveIntegerArrUtil.isOdd(this.values);
    }

    public NewIntegerNumber abs() {
        if(this.sign == NumberConstant.NEGATIVE){
            return new NewIntegerNumber(NumberConstant.POSITIVE, this.values);
        }else{
            return this;
        }
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
        if(number.startsWith("0")){
            int pz = 0;
            for(int i = 0, len = number.length(); i < len - 1; i++){
                if(number.charAt(i) == '0'){
                    pz++;
                }else{
                    break;
                }
            }
            number = number.substring(pz);
        }
        return number;
    }

    public static NewIntegerNumber valueOf(int number){
        if(number == 0){
            return ZERO;
        }else if(number == 1){
            return ONE;
        }else{
            return new NewIntegerNumber(NumberConstant.POSITIVE, new int[]{number});
        }
    }

    public static NewIntegerNumber valueOf(String number){
        number = fixNumber(number);
        if("0".equals(number) || "-0".equals(number)){
            return ZERO;
        }else if("1".equals(number)){
            return ONE;
        }else{
            if(number.startsWith("-")){
                return new NewIntegerNumber(NumberConstant.NEGATIVE, number.substring(1));
            }else{
                return new NewIntegerNumber(NumberConstant.POSITIVE, number);
            }
        }
    }


    /*
     * 字面量, 用于存储Number的可读表达形式
     * 由于每一个字符串是用char数组进行存储的, 所以有可能一个char数组存储不下, 这时就需要过个字符串来存储一个数字
     * 需要注意的是, 这个数组属于big-endian, 最低位存储在0号索引中
     */
    private String[] literal;

    private static final int SINGLE_LITERAL_ARRAY_ELEMENT_LENGTH = PositiveIntegerArrUtil.SINGLE_ELEMENT_LEN * (Integer.MAX_VALUE >> 4);

    private static final String FILL_ELEMENT;

    static {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < PositiveIntegerArrUtil.SINGLE_ELEMENT_LEN){
            sb.append("0");
            i++;
        }
        FILL_ELEMENT = sb.toString();
    }

    @Override
    public String toString() {
        if(literal == null){
            int len = values.length / SINGLE_LITERAL_ARRAY_ELEMENT_LENGTH * 10 + (values.length % SINGLE_LITERAL_ARRAY_ELEMENT_LENGTH == 0 ? 0 : 1);
            literal = new String[len];
            int j = 0;
            StringBuilder sb = new StringBuilder();
            if(this.sign == NumberConstant.NEGATIVE){ sb.append('-'); }

            sb.append(values[values.length - 1]);
            for(int i = values.length - 2; i >= 0; i--){
                if(sb.length() == SINGLE_LITERAL_ARRAY_ELEMENT_LENGTH){
                    literal[j++] = sb.toString();
                    sb = new StringBuilder();
                }
                int val = values[i];
                if(val == 0){
                    sb.append(FILL_ELEMENT);
                }else {
                    if(val < PositiveIntegerArrUtil.SCALE / 10){
                        while(val < PositiveIntegerArrUtil.SCALE / 10){
                            sb.append(0);
                            val *= 10;
                        }
                    }
                    sb.append(values[i]);
                }
            }
            if(sb.length() > 0){
                literal[j] = sb.toString();
            }
        }
        return literal[0] + (literal.length > 1 ? "..." : "");
    }

    public boolean equals(Object o){
        if(o == this){ return true; }
        if(o == null || o.getClass() != NewIntegerNumber.class){
            return false;
        }
        NewIntegerNumber that = (NewIntegerNumber) o;
        int mark = PositiveIntegerArrUtil.compare(this.values, that.values);
        return mark == 0;
    }

    @Override
    public String toDecimal(int precision) {
        return this.toString();
    }

    public static class Remainder{
        private NewIntegerNumber remainder;

        public Remainder(){ }

        private Remainder(NewIntegerNumber remainder) {
            this.remainder = remainder;
        }
        public NewIntegerNumber getRemainder() {
            return remainder;
        }
    }
}
