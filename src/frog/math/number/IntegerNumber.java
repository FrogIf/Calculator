package frog.math.number;

import frog.math.exception.DivideByZeroException;
import frog.math.operation.Add;
import frog.math.operation.Mult;
import frog.math.operation.Not;
import frog.math.operation.Compare;

/**
 * 整数, 不可变类型
 */
public class IntegerNumber extends AbstractNumber implements Add, Not, Mult, Compare {

    /**
     * 正
     */
    private static final int SIGN_POSITIVE = 0;

    /**
     * 负
     */
    private static final int SIGN_NEGATIVE = 1;

    /**
     * 符号, 记录该数是正数(SIGN_POSITIVE), 还是负数(SIGN_NEGATIVE)
     */
    private final int sign;

    /**
     * 非0高位在values数组中的索引位置
     */
    private final int highPos;

    /**
     * 0
     */
    public static final IntegerNumber ZERO = new IntegerNumber(SIGN_POSITIVE, PositiveIntegerUtil.ZERO);

    /**
     * 1
     */
    public static final IntegerNumber ONE = new IntegerNumber(SIGN_POSITIVE, PositiveIntegerUtil.ONE);

    /**
     * -1
     */
    public static final IntegerNumber NEGATIVE_ONE = new IntegerNumber(SIGN_NEGATIVE, PositiveIntegerUtil.ONE);

    /*
     * 用于存储number的真实值, 可读数字中每9个数作为一个元素存入该数组中, 低位存储于低索引处, 高位存储于高索引处(little-endian)
     * 例如: 666666123456789 -- new int[]{123456789, 666666}
     */
    private final int[] values;

    private IntegerNumber(int sign, String literal) {
        this.sign = sign;
        this.literal = literal;
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
        this.highPos = values.length - 1;
    }

    private IntegerNumber(int sign, int[] values){
        if(values == null || values.length == 0){
            throw new IllegalArgumentException("empty number.");
        }
        this.highPos = PositiveIntegerUtil.highPos(values);
        this.values = values;
        this.sign = sign;
    }

    @Override
    public IntegerNumber add(Add num) {
        if(num instanceof IntegerNumber){
            return accumulation(this, (IntegerNumber)num, SIGN_POSITIVE);
        }else{
            return null;
        }
    }

    /**
     * 对两个数进行相加/减
     * @param left 运算左侧数
     * @param right 运算右侧数
     * @param operator 指定运算类型, 0 - 相加, 1 - 相减
     */
    private static IntegerNumber accumulation(IntegerNumber left, IntegerNumber right, int operator){
        IntegerNumber result;
        if((left.sign ^ right.sign) == operator){
            int[] values = PositiveIntegerUtil.add(left.values, left.highPos, right.values, right.highPos);
            result = new IntegerNumber(left.sign, values);
        }else{
            int c = PositiveIntegerUtil.compare(left.values, left.highPos, right.values, right.highPos);
            if(c == 0){
                result = ZERO;
            }else{
                int[] values;
                byte sign = SIGN_POSITIVE;
                if(c < 0){
                    values = PositiveIntegerUtil.subtract(right.values, right.highPos, left.values, left.highPos);
                    sign = SIGN_NEGATIVE;
                }else{
                    values = PositiveIntegerUtil.subtract(left.values, left.highPos, right.values, right.highPos);
                }
                result = new IntegerNumber(left.sign ^ sign, values);
            }
        }
        return result;
    }

    /**
     * 有int类型参与的加法
     * @param num 加数
     * @return 和
     */
    public IntegerNumber add(int num){
        return accumulationOneWord(num, SIGN_POSITIVE);
    }

    private IntegerNumber accumulationOneWord(int num, int operator){
        if(num == 0){ return this; }

        int absNum = num < 0 ? -num : num;

        boolean sameSign = num < 0 == (this.sign == SIGN_NEGATIVE) && operator == SIGN_POSITIVE;    // true - 同号, false - 异号
        if(absNum < PositiveIntegerUtil.SCALE){
            if(sameSign){
                return new IntegerNumber(this.sign, PositiveIntegerUtil.addOneWord(this.values, this.highPos, absNum));
            }else{
                return new IntegerNumber(this.sign, PositiveIntegerUtil.subtractOneWord(this.values, this.highPos, absNum));
            }
        }else{
            int[] temp = new int[2];
            temp[0] = absNum % PositiveIntegerUtil.SCALE;
            temp[1] = absNum / PositiveIntegerUtil.SCALE;
            if(sameSign){
                return new IntegerNumber(this.sign, PositiveIntegerUtil.add(this.values, this.highPos, temp, 1));
            }else{
                int c = PositiveIntegerUtil.compare(this.values, this.highPos, temp, 1);
                if(c == 0){
                    return ZERO;
                }else if(c > 0){
                    return new IntegerNumber(this.sign, PositiveIntegerUtil.subtract(this.values, this.highPos, temp, 1));
                }else{
                    return new IntegerNumber(1 ^ this.sign, PositiveIntegerUtil.subtract(temp, 1, this.values, this.highPos));
                }
            }
        }
    }

    @Override
    public Mult mult(Mult num) {
        if(num instanceof IntegerNumber){
            IntegerNumber intNum = (IntegerNumber) num;
            return new IntegerNumber(this.sign ^ intNum.sign, PositiveIntegerUtil.multiply(this.values, this.highPos, intNum.values, intNum.highPos));
        }else{
            return null;
        }
    }

    @Override
    public IntegerNumber not() {
        return new IntegerNumber(1 ^ this.sign, this.values);
    }

    @Override
    public IntegerNumber abs() {
        if(this.sign == SIGN_NEGATIVE){
            return new IntegerNumber(SIGN_POSITIVE, this.values);
        }else{
            return this;
        }
    }

    @Override
    public boolean isPositive() {
        return this.sign == SIGN_POSITIVE;
    }

    /**
     * 除法
     * @param num 除数
     * @return 商, 余数会被舍去
     */
    public IntegerNumber div(IntegerNumber num){
        if(num.values.length == 1 && num.values[0] == 0){
            throw new DivideByZeroException();
        }
        int[][] result = PositiveIntegerUtil.divide(this.values, this.highPos, num.values, num.highPos);
        return new IntegerNumber(this.sign ^ num.sign, result[0]);
    }

    /**
     * 除法
     * @param num 除数
     * @param remainder 余数持有者, 如果该对象不为null, 运算结果中的余数会被放入该对象中
     * @return 商
     */
    public IntegerNumber div(IntegerNumber num, Remainder remainder){
        int[][] result = PositiveIntegerUtil.divide(this.values, this.highPos, num.values, num.highPos);
        if(remainder != null){
            remainder.value = new IntegerNumber(SIGN_POSITIVE, result[1]);
        }
        return new IntegerNumber(this.sign ^ num.sign, result[0]);
    }

    /**
     * 余数持有者
     */
    public static class Remainder{
        private IntegerNumber value;
        public IntegerNumber getValue() {
            return value;
        }
    }

    /**
     * 获取本身与另一个数的最大公约数
     * @param num 参与运算的数
     * @return 最大公约数
     */
    public IntegerNumber gcd(IntegerNumber num){
        return new IntegerNumber(SIGN_POSITIVE, PositiveIntegerUtil.gcd(this.values, this.highPos, num.values, num.highPos));
    }

    /**
     * 自减1
     * @return 返回自减结果
     */
    public IntegerNumber decrease(){
        if(this.values.length == 1 && this.values[0] == 0){
            return NEGATIVE_ONE;
        }
        if(this.sign == SIGN_POSITIVE){
            int[] res = PositiveIntegerUtil.subtractOneWord(this.values, this.highPos, 1);
            return new IntegerNumber(this.sign, res);
        }else{
            int[] res = PositiveIntegerUtil.addOneWord(this.values, this.highPos, 1);
            return new IntegerNumber(this.sign, res);
        }
    }

    /**
     * 自加1
     * @return 自加结果
     */
    public IntegerNumber increase(){
        if(this.sign == SIGN_POSITIVE){
            return new IntegerNumber(this.sign, PositiveIntegerUtil.addOneWord(this.values, this.highPos, 1));
        }else{
            int[] res = PositiveIntegerUtil.subtractOneWord(this.values, this.highPos, 1);
            if(this.values.length == 1 && values[0] == 0){
                return ZERO;
            }else{
                return new IntegerNumber(this.sign, res);
            }
        }
    }

    /**
     * 判断是否是奇数
     * @return true - 奇数; false - 偶数
     */
    public boolean isOdd(){
        return PositiveIntegerUtil.isOdd(this.values);
    }

        /**
     * 十进制左移
     * @param bcount 左移位数
     * @return 左移后的结果
     */
    public IntegerNumber decLeftShift(int bcount){
        return new IntegerNumber(this.sign, PositiveIntegerUtil.decLeftShift(this.values, this.highPos, bcount));
    }

    /**
     * 获取该数含有多少个十进制位
     * @return 十进制位个数
     */
    public int decBits(){
        int b = highPos * PositiveIntegerUtil.SINGLE_ELEMENT_LEN;
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

    /**
     * int类型转IntegerNumber
     * @param number 待转int
     * @return 转换结果
     */
    public static IntegerNumber valueOf(int number){
        if(number == 0){
            return ZERO;
        }else if(number == 1){
            return ONE;
        }else if(number == -1){
            return NEGATIVE_ONE;
        }else {
            byte sign;
            int absNum;
            if(number < 0){
                absNum = -number;
                sign = SIGN_NEGATIVE;
            }else{
                absNum = number;
                sign = SIGN_POSITIVE;
            }

            int[] result;
            if(absNum < PositiveIntegerUtil.SCALE){
                result = new int[1];
                result[0] = absNum;
            }else{
                result = new int[2];
                result[0] = absNum % PositiveIntegerUtil.SCALE;
                result[1] = absNum / PositiveIntegerUtil.SCALE;
            }

            return new IntegerNumber(sign, result);
        }
    }

    /**
     * string解析为IntegerNumber
     * @param number 待解析的数字
     * @return 解析结果, 如果输入为空或者不是以数字开头, 将会报错
     */
    public static IntegerNumber valueOf(String number){
        number = fixNumber(number);
        if("0".equals(number) || "-0".equals(number)){
            return ZERO;
        }else if("1".equals(number)){
            return ONE;
        }else{
            if(number.startsWith("-")){
                return new IntegerNumber(SIGN_NEGATIVE, number.substring(1));
            }else{
                return new IntegerNumber(SIGN_POSITIVE, number);
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

    @Override
    public int compareTo(Compare that) {
        if(!(that instanceof IntegerNumber)){
            throw new UnsupportedOperationException("");
        }

        IntegerNumber o = (IntegerNumber) that;
        if(this.sign == o.sign){
            if(this.sign == SIGN_POSITIVE){
                return PositiveIntegerUtil.compare(this.values, this.highPos, o.values, o.highPos);
            }else{
                return -PositiveIntegerUtil.compare(this.values, this.highPos, o.values, o.highPos);
            }
        }else{
            return 1 - (this.sign << 1);
        }
    }

    public int compareTo(int num){
        if(num == 0){
            return this.values[this.highPos] - this.sign << 30;
        }else if(num < 0 == (this.sign == SIGN_NEGATIVE) && this.highPos < 2){
            long mark = ((long)this.values[this.highPos] * PositiveIntegerUtil.SCALE * this.highPos + this.values[0] - (num < 0 ? -num : num));
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

    private String literal;

    private static final String FILL_ELEMENT;

    private static final int MAX_ENTIRELY_DISPLAY_POS = (Integer.MAX_VALUE - 10) / PositiveIntegerUtil.SINGLE_ELEMENT_LEN - 1;

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
            if(this.highPos > MAX_ENTIRELY_DISPLAY_POS){
                // 字符串已经不能展示这个数字了, 采用科学计数法
                long n = ((long)this.highPos - 1) * PositiveIntegerUtil.SINGLE_ELEMENT_LEN;
                int hVal = this.values[this.highPos];
                while(hVal > 10){
                    n++;
                    hVal /= 10;
                }
                literal = hVal + "E" + n;
            }else{
                StringBuilder sb = new StringBuilder();
                if(this.sign == SIGN_NEGATIVE){ sb.append('-'); }
                for(int i = highPos; i >= 0; i--){
                    int val = values[i];
                    if(val == 0){
                        sb.append(FILL_ELEMENT);
                    }else {
                        while(val < PositiveIntegerUtil.SCALE / 10){
                            sb.append(0);
                            val *= 10;
                        }
                        sb.append(values[i]);
                    }
                }
                literal = sb.toString();
            }
        }
        return literal;
    }

}
