package frog.calculator.math.rational;

import frog.calculator.math.INumber;
import frog.calculator.math.NumberConstant;
import frog.calculator.math.exception.DivideByZeroException;

public class IntegerNumber implements INumber, Comparable<IntegerNumber> {

    private static final int[] ZERO_VALUES = new int[1];

    private static final int[] ONE_VALUES = new int[]{1};

    public static final IntegerNumber ZERO = new IntegerNumber(NumberConstant.POSITIVE, ZERO_VALUES);

    public static final IntegerNumber ONE = new IntegerNumber(NumberConstant.POSITIVE, ONE_VALUES);

    public static final IntegerNumber N_ONE = new IntegerNumber(NumberConstant.NEGATIVE, ONE_VALUES);

    private final byte sign;

    /*
     * 用于存储number的真实值, 可读数字中每9个数作为一个元素存入该数组中, 低位存储于低索引处, 高位存储于高索引处(little-endian)
     */
    private final int[] values;

    public IntegerNumber(byte sign, String literal) {
        this.sign = sign;
        this.literal = new String[1];
        this.literal[0] = literal;
        int len = literal.length();
        int vl = len / PositiveIntegerUtil.SINGLE_ELEMENT_LEN + (len % PositiveIntegerUtil.SINGLE_ELEMENT_LEN == 0 ? 0 : 1);
        this.values = new int[vl];

        int temp = 0;
        int step = 0;
        int move = 1;
        int j = 0;
        for(int i = len - 1; i >= 0; i--){
            temp += (literal.charAt(i) - '0') * move;
            move *= 10;
            step++;
            if(step % PositiveIntegerUtil.SINGLE_ELEMENT_LEN == 0){
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

    private IntegerNumber(byte sign, int[] values){
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
    private static IntegerNumber accumulation(IntegerNumber left, IntegerNumber right, byte operator){
        IntegerNumber result;
        if((left.sign ^ right.sign) == operator){
            int[] values = PositiveIntegerUtil.add(left.values, right.values);
            result = new IntegerNumber(left.sign, values);
        }else{
            int c = PositiveIntegerUtil.compare(left.values, right.values);
            if(c == 0){
                result = ZERO;
            }else{
                int[] values;
                byte sign = NumberConstant.POSITIVE;
                if(c < 0){
                    values = PositiveIntegerUtil.subtract(right.values, left.values);
                    sign = NumberConstant.NEGATIVE;
                }else{
                    values = PositiveIntegerUtil.subtract(left.values, right.values);
                }
                result = new IntegerNumber((byte)(left.sign ^ sign), values);
            }
        }
        return result;
    }

    public IntegerNumber add(IntegerNumber num){
        return accumulation(this, num, NumberConstant.POSITIVE);
    }

    public IntegerNumber sub(IntegerNumber num){
        return accumulation(this, num, NumberConstant.NEGATIVE);
    }

    public IntegerNumber mult(IntegerNumber num){
        return new IntegerNumber((byte) (this.sign ^ num.sign), PositiveIntegerUtil.multiply(this.values, num.values));
    }

    public IntegerNumber div(IntegerNumber num, Remainder remainder){
        int[][] result = PositiveIntegerUtil.division(this.values, num.values);
        if(remainder != null){
            remainder.remainder = new IntegerNumber(NumberConstant.POSITIVE, result[1]);
        }
        return new IntegerNumber((byte) (this.sign ^ num.sign), result[0]);
    }

    public IntegerNumber div(IntegerNumber num){
        if(num.values.length == 1 && num.values[0] == 0){
            throw new DivideByZeroException();
        }
        int[][] result = PositiveIntegerUtil.division(this.values, num.values);
        return new IntegerNumber((byte) (this.sign ^ num.sign), result[0]);
    }

    @Override
    public IntegerNumber not() {
        return new IntegerNumber((byte) (1 ^ this.sign), this.values);
    }

    public IntegerNumber gcd(IntegerNumber num){
        return new IntegerNumber(NumberConstant.POSITIVE, PositiveIntegerUtil.gcd(this.values, num.values));
    }

    public boolean isOdd(){
        return PositiveIntegerUtil.isOdd(this.values);
    }

    public IntegerNumber abs() {
        if(this.sign == NumberConstant.NEGATIVE){
            return new IntegerNumber(NumberConstant.POSITIVE, this.values);
        }else{
            return this;
        }
    }

    /**
     * 自减1
     * @return 返回自减结果
     */
    public IntegerNumber decrease(){
        if(this.values.length == 1 && this.values[0] == 0){
            return N_ONE;
        }
        if(this.sign == NumberConstant.POSITIVE){
            int[] res = PositiveIntegerUtil.decrease(this.values);
            return new IntegerNumber(this.sign, res);
        }else{
            int[] res = PositiveIntegerUtil.increase(this.values);;
            return new IntegerNumber(this.sign, res);
        }
    }

    public IntegerNumber increase(){
        if(this.sign == NumberConstant.POSITIVE){
            return new IntegerNumber(this.sign, PositiveIntegerUtil.increase(this.values));
        }else{
            int[] res = PositiveIntegerUtil.decrease(this.values);
            if(this.values.length == 1 && values[0] == 0){
                return ZERO;
            }else{
                return new IntegerNumber(this.sign, res);
            }
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

    public static IntegerNumber valueOf(int number){
        if(number == 0){
            return ZERO;
        }else if(number == 1){
            return ONE;
        }else{
            return new IntegerNumber(NumberConstant.POSITIVE, new int[]{number});
        }
    }

    public static IntegerNumber valueOf(String number){
        number = fixNumber(number);
        if("0".equals(number) || "-0".equals(number)){
            return ZERO;
        }else if("1".equals(number)){
            return ONE;
        }else{
            if(number.startsWith("-")){
                return new IntegerNumber(NumberConstant.NEGATIVE, number.substring(1));
            }else{
                return new IntegerNumber(NumberConstant.POSITIVE, number);
            }
        }
    }


    /*
     * 字面量, 用于存储Number的可读表达形式
     * 由于每一个字符串是用char数组进行存储的, 所以有可能一个char数组存储不下, 这时就需要过个字符串来存储一个数字
     * 需要注意的是, 这个数组属于big-endian, 最低位存储在0号索引中
     */
    private String[] literal;

    private static final int SINGLE_LITERAL_ARRAY_ELEMENT_LENGTH = PositiveIntegerUtil.SINGLE_ELEMENT_LEN * (Integer.MAX_VALUE >> 4);

    private static final String FILL_ELEMENT;

    static {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < PositiveIntegerUtil.SINGLE_ELEMENT_LEN){
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
                    if(val < PositiveIntegerUtil.SCALE / 10){
                        while(val < PositiveIntegerUtil.SCALE / 10){
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
        if(o == null || o.getClass() != IntegerNumber.class){
            return false;
        }
        IntegerNumber that = (IntegerNumber) o;
        int mark = PositiveIntegerUtil.compare(this.values, that.values);
        return mark == 0;
    }

    public byte getSign(){
        return this.sign;
    }

    @Override
    public String toDecimal(int precision) {
        return this.toString();
    }

    @Override
    public int compareTo(IntegerNumber o) {
        if(this.sign == o.sign){
            if(this.sign == NumberConstant.POSITIVE){
                return PositiveIntegerUtil.compare(this.values, o.values);
            }else{
                return -PositiveIntegerUtil.compare(this.values, o.values);
            }
        }else{
            if(this.sign == NumberConstant.POSITIVE){
                return 1;
            }else{
                return -1;
            }
        }
    }

    public static class Remainder{
        private IntegerNumber remainder;

        public Remainder(){ }

        private Remainder(IntegerNumber remainder) {
            this.remainder = remainder;
        }
        public IntegerNumber getRemainder() {
            return remainder;
        }
    }
}
