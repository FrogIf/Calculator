package frog.calculator.math.number;

import frog.calculator.math.exception.DivideByZeroException;

/**
 * 整数
 */
public final class IntegerNumber implements INumber, Comparable<IntegerNumber> {

    public static final byte POSITIVE = 0;

    public static final byte NEGATIVE = 1;

    public static final IntegerNumber ZERO = new IntegerNumber(POSITIVE, PositiveInteger.ZERO);

    public static final IntegerNumber ONE = new IntegerNumber(POSITIVE, PositiveInteger.ONE);

    public static final IntegerNumber N_ONE = new IntegerNumber(NEGATIVE, PositiveInteger.ONE);

    private final byte sign;

    private final int highPos;  // 首位高度

    /*
     * 用于存储number的真实值, 可读数字中每9个数作为一个元素存入该数组中, 低位存储于低索引处, 高位存储于高索引处(little-endian)
     */
    private final int[] values;

    private IntegerNumber(byte sign, String literal) {
        this.sign = sign;
        this.literal = new String[1];
        this.literal[0] = literal;
        int len = literal.length();
        int vl = len / PositiveInteger.SINGLE_ELEMENT_LEN + (len % PositiveInteger.SINGLE_ELEMENT_LEN == 0 ? 0 : 1);
        this.values = new int[vl];

        int temp = 0;
        int step = 0;
        int move = 1;
        int j = 0;
        for(int i = len - 1; i >= 0; i--){
            temp += (literal.charAt(i) - '0') * move;
            move *= 10;
            step++;
            if(step % PositiveInteger.SINGLE_ELEMENT_LEN == 0){
                values[j++] = temp;
                step = 0;
                move = 1;
                temp = 0;
            }
        }
        if(temp > 0){
            values[j] = temp;
        }
        this.highPos = values.length - 1;
    }

    private IntegerNumber(byte sign, int[] values){
        if(values == null || values.length == 0){
            throw new IllegalArgumentException("empty number.");
        }
        this.highPos = PositiveInteger.highPos(values);
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
            int[] values = PositiveInteger.add(left.values, left.highPos, right.values, right.highPos);
            result = new IntegerNumber(left.sign, values);
        }else{
            int c = PositiveInteger.compare(left.values, left.highPos, right.values, right.highPos);
            if(c == 0){
                result = ZERO;
            }else{
                int[] values;
                byte sign = POSITIVE;
                if(c < 0){
                    values = PositiveInteger.subtract(right.values, right.highPos, left.values, left.highPos);
                    sign = NEGATIVE;
                }else{
                    values = PositiveInteger.subtract(left.values, left.highPos, right.values, right.highPos);
                }
                result = new IntegerNumber((byte)(left.sign ^ sign), values);
            }
        }
        return result;
    }

    private IntegerNumber accumulationOneWord(int num, byte operator){
        if(num == 0){ return this; }

        int absNum = num < 0 ? -num : num;
        if(num < 0 == (this.sign == NEGATIVE) && operator == POSITIVE){  // 同号
            if(absNum < PositiveInteger.SCALE){
                return new IntegerNumber(this.sign, PositiveInteger.addOneWord(this.values, this.highPos, absNum));
            }else{
                int[] temp = new int[2];
                temp[0] = absNum % PositiveInteger.SCALE;
                temp[1] = absNum / PositiveInteger.SCALE;
                return new IntegerNumber(this.sign, PositiveInteger.add(this.values, this.highPos, temp, 1));
            }
        }else{ // 异号
            if(absNum < PositiveInteger.SCALE){
                if(this.values.length > 1 || this.values[0] > absNum){
                    return new IntegerNumber(this.sign, PositiveInteger.subtractOneWord(this.values, this.highPos, absNum));
                }else{
                    int res = absNum - this.values[0];
                    if(res == 0){ return ZERO; }
                    else{ return new IntegerNumber((byte) (1 ^ this.sign), new int[]{res}); }
                }
            }else{
                int[] temp = new int[2];
                temp[0] = absNum % PositiveInteger.SCALE;
                temp[1] = absNum / PositiveInteger.SCALE;
                int c = PositiveInteger.compare(this.values, this.highPos, temp, 1);
                if(c == 0){
                    return ZERO;
                }else if(c > 0){
                    return new IntegerNumber(this.sign, PositiveInteger.subtract(this.values, this.highPos, temp, 1));
                }else{
                    return new IntegerNumber((byte) (1 ^ this.sign), PositiveInteger.subtract(temp, 1,this.values, this.highPos));
                }
            }
        }
    }

    public IntegerNumber add(IntegerNumber num){
        return accumulation(this, num, POSITIVE);
    }

    public IntegerNumber add(int num){
        return accumulationOneWord(num, POSITIVE);
    }

    public IntegerNumber sub(IntegerNumber num){
        return accumulation(this, num, NEGATIVE);
    }

    public IntegerNumber sub(int num){
        return accumulationOneWord(num, NEGATIVE);
    }

    public IntegerNumber mult(IntegerNumber num){
        return new IntegerNumber((byte) (this.sign ^ num.sign), PositiveInteger.multiply(this.values, this.highPos, num.values, num.highPos));
    }

    public IntegerNumber div(IntegerNumber num, Remainder remainder){
        int[][] result = PositiveInteger.divide(this.values, this.highPos, num.values, num.highPos);
        if(remainder != null){
            remainder.remainder = new IntegerNumber(POSITIVE, result[1]);
        }
        return new IntegerNumber((byte) (this.sign ^ num.sign), result[0]);
    }

    public IntegerNumber div(IntegerNumber num){
        if(num.values.length == 1 && num.values[0] == 0){
            throw new DivideByZeroException();
        }
        int[][] result = PositiveInteger.divide(this.values, this.highPos, num.values, num.highPos);
        return new IntegerNumber((byte) (this.sign ^ num.sign), result[0]);
    }

    @Override
    public IntegerNumber not() {
        return new IntegerNumber((byte) (1 ^ this.sign), this.values);
    }

    public IntegerNumber gcd(IntegerNumber num){
        return new IntegerNumber(POSITIVE, PositiveInteger.gcd(this.values, this.highPos, num.values, num.highPos));
    }

    public boolean isOdd(){
        return PositiveInteger.isOdd(this.values);
    }

    public IntegerNumber abs() {
        if(this.sign == NEGATIVE){
            return new IntegerNumber(POSITIVE, this.values);
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
        if(this.sign == POSITIVE){
            int[] res = PositiveInteger.subtractOneWord(this.values, this.highPos, 1);
            return new IntegerNumber(this.sign, res);
        }else{
            int[] res = PositiveInteger.addOneWord(this.values, this.highPos, 1);
            return new IntegerNumber(this.sign, res);
        }
    }

    /**
     * 自加1
     * @return 自加结果
     */
    public IntegerNumber increase(){
        if(this.sign == POSITIVE){
            return new IntegerNumber(this.sign, PositiveInteger.addOneWord(this.values, this.highPos, 1));
        }else{
            int[] res = PositiveInteger.subtractOneWord(this.values, this.highPos, 1);
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
            byte sign;
            int absNum;
            if(number < 0){
                absNum = -number;
                sign = NEGATIVE;
            }else{
                absNum = number;
                sign = POSITIVE;
            }

            int[] result;
            if(absNum < PositiveInteger.SCALE){
                result = new int[1];
                result[0] = absNum;
            }else{
                result = new int[2];
                result[0] = absNum % PositiveInteger.SCALE;
                result[1] = absNum / PositiveInteger.SCALE;
            }

            return new IntegerNumber(sign, result);
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
                return new IntegerNumber(NEGATIVE, number.substring(1));
            }else{
                return new IntegerNumber(POSITIVE, number);
            }
        }
    }


    /*
     * 字面量, 用于存储Number的可读表达形式
     * 由于每一个字符串是用char数组进行存储的, 所以有可能一个char数组存储不下, 这时就需要过个字符串来存储一个数字
     * 需要注意的是, 这个数组属于big-endian, 最低位存储在0号索引中
     */
    private String[] literal;

    private static final int SINGLE_LITERAL_ARRAY_ELEMENT_LENGTH = PositiveInteger.SINGLE_ELEMENT_LEN * (Integer.MAX_VALUE >> 4);

    private static final String FILL_ELEMENT;

    static {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < PositiveInteger.SINGLE_ELEMENT_LEN){
            sb.append("0");
            i++;
        }
        FILL_ELEMENT = sb.toString();
    }

    @Override
    public String toString() {
        if(literal == null){
            int len = (this.highPos + 1) / SINGLE_LITERAL_ARRAY_ELEMENT_LENGTH * 10 + (values.length % SINGLE_LITERAL_ARRAY_ELEMENT_LENGTH == 0 ? 0 : 1);
            literal = new String[len];
            int j = 0;
            StringBuilder sb = new StringBuilder();
            if(this.sign == NEGATIVE){ sb.append('-'); }

            sb.append(values[highPos]);
            for(int i = highPos - 1; i >= 0; i--){
                if(sb.length() == SINGLE_LITERAL_ARRAY_ELEMENT_LENGTH){
                    literal[j++] = sb.toString();
                    sb = new StringBuilder();
                }
                int val = values[i];
                if(val == 0){
                    sb.append(FILL_ELEMENT);
                }else {
                    if(val < PositiveInteger.SCALE / 10){
                        while(val < PositiveInteger.SCALE / 10){
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

    /**
     * 十进制左移
     * @param bcount 左移位数
     * @return 左移后的结果
     */
    public IntegerNumber decLeftShift(int bcount){
        return new IntegerNumber(this.sign, PositiveInteger.decLeftShift(this.values, this.highPos, bcount));
    }

    /**
     * 获取该数含有多少个十进制位
     * @return 十进制位个数
     */
    public int decBits(){
        int b = highPos * PositiveInteger.SINGLE_ELEMENT_LEN;
        int v = this.values[highPos];
        while(v > 0){
            v /= 10;
            b++;
        }
        if(b == 0){
            b = 1;
        }
        return b;
    }

    public boolean equals(Object o){
        if(o == this){ return true; }
        if(o == null || o.getClass() != IntegerNumber.class){
            return false;
        }
        IntegerNumber that = (IntegerNumber) o;
        int mark = PositiveInteger.compare(this.values, this.highPos, that.values, that.highPos);
        return mark == 0;
    }

    public byte getSign(){
        return this.sign;
    }

    @Override
    public String toDecimal(int precision) {
        return this.toString();
    }

    public int compareTo(int num){
        if(num == 0){
            return this.values[this.highPos] - this.sign << 30;
        }else if(num < 0 == (this.sign == NEGATIVE) && this.highPos < 2){
            long mark = ((long)this.values[this.highPos] * PositiveInteger.SCALE * this.highPos + this.values[0] - (num < 0 ? -num : num));
            if(mark == 0){
                return 0;
            }else if(mark > 0){
                return 1 - (this.sign << 1);
            }else{
                return (this.sign << 1) - 1;
            }
        }else{
            return 1 - (this.sign << 1);
        }
    }

    @Override
    public int compareTo(IntegerNumber o) {
        if(this.sign == o.sign){
            if(this.sign == POSITIVE){
                return PositiveInteger.compare(this.values, this.highPos, o.values, o.highPos);
            }else{
                return -PositiveInteger.compare(this.values, this.highPos, o.values, o.highPos);
            }
        }else{
            if(this.sign == POSITIVE){
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
