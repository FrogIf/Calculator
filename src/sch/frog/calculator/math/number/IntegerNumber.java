package sch.frog.calculator.math.number;

import sch.frog.calculator.math.exception.DivideByZeroException;
import sch.frog.calculator.util.StringUtils;

/**
 * 整数
 */
public final class IntegerNumber extends AbstractBaseNumber implements Comparable<IntegerNumber> {

    /**
     * 正
     */
    static final int POSITIVE = 0;

    /**
     * 负
     */
    static final int NEGATIVE = 1;

    /**
     * 符号, 记录该数是正数(POSITIVE), 还是负数(NEGATIVE)
     */
    private final int sign;

    /**
     * 非0高位在values数组中的索引位置
     */
    private final int highPos;

    /**
     * 0
     */
    public static final IntegerNumber ZERO = new IntegerNumber(POSITIVE, PositiveIntegerUtil.ZERO);

    /**
     * 1
     */
    public static final IntegerNumber ONE = new IntegerNumber(POSITIVE, PositiveIntegerUtil.ONE);

    /**
     * -1
     */
    public static final IntegerNumber NEGATIVE_ONE = new IntegerNumber(NEGATIVE, PositiveIntegerUtil.ONE);

    /*
     * 用于存储number的真实值, 可读数字中每9个数作为一个元素存入该数组中, 低位存储于低索引处, 高位存储于高索引处(little-endian)
     * 例如: 666666123456789 在数组中排列为: 123456789, 666666
     */
    private final int[] values;

    /**
     * 整数
     * @param sign 符号标记, 0 - 整数, 1 - 负数
     * @param literal 字面量
     */
    private IntegerNumber(int sign, String literal) {
        this.sign = sign;
        this.literal = literal;
        this.values = generateVal(literal);
        this.highPos = values.length - 1;
    }

    /**
     * 根据字面量生成值数组, 并且该值数组是满的
     * @param literal 字面量
     * @return 值数组
     */
    private static int[] generateVal(String literal){
        int len = literal.length();
        int vl = len / PositiveIntegerUtil.SINGLE_ELEMENT_LEN + (len % PositiveIntegerUtil.SINGLE_ELEMENT_LEN == 0 ? 0 : 1);
        int[] valArr = new int[vl];

        int temp = 0;
        int step = 0;
        int move = 1;
        int j = 0;
        for(int i = len - 1; i >= 0; i--){
            temp += (literal.charAt(i) - '0') * move;
            move *= 10;
            step++;
            if(step % PositiveIntegerUtil.SINGLE_ELEMENT_LEN == 0){
                valArr[j++] = temp;
                step = 0;
                move = 1;
                temp = 0;
            }
        }
        if(temp > 0){
            valArr[j] = temp;
        }
        return valArr;
    }

    private IntegerNumber(int sign, int[] values){
        if(values == null || values.length == 0){
            throw new IllegalArgumentException("empty number.");
        }
        this.highPos = PositiveIntegerUtil.highPos(values);
        this.values = values;
        this.sign = sign;
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
                byte sign = POSITIVE;
                if(c < 0){
                    values = PositiveIntegerUtil.subtract(right.values, right.highPos, left.values, left.highPos);
                    sign = NEGATIVE;
                }else{
                    values = PositiveIntegerUtil.subtract(left.values, left.highPos, right.values, right.highPos);
                }
                result = new IntegerNumber(left.sign ^ sign, values);
            }
        }
        return result;
    }

    /**
     * IntegerNumber与int值进行加/减运算
     * @param num 参与运算的数
     * @param operator 参与的运算, 与
     * @return
     */
    private IntegerNumber accumulationOneWord(int num, int operator){
        if(num == 0){ return this; }

        int absNum = num < 0 ? -num : num;

        boolean sameSign = num < 0 == (this.sign == NEGATIVE) && operator == POSITIVE;    // true - 同号, false - 异号
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

    /**
     * 加法
     * @param num 加数
     * @return 和
     */
    public IntegerNumber add(IntegerNumber num){
        return accumulation(this, num, POSITIVE);
    }

    /**
     * 加法
     * @param num 加数
     * @return 和
     */
    public IntegerNumber add(int num){
        return accumulationOneWord(num, POSITIVE);
    }

    /**
     * 减法
     * @param num 减数
     * @return 差
     */
    public IntegerNumber sub(IntegerNumber num){
        return accumulation(this, num, NEGATIVE);
    }

    /**
     * 减法
     * @param num 减数
     * @return 差
     */
    public IntegerNumber sub(int num){
        return accumulationOneWord(num, NEGATIVE);
    }

    /**
     * 乘法
     * @param num 乘数
     * @return 积
     */
    public IntegerNumber mult(IntegerNumber num){
        return new IntegerNumber((byte) (this.sign ^ num.sign), PositiveIntegerUtil.multiply(this.values, this.highPos, num.values, num.highPos));
    }

    /**
     * 乘法
     * @param num 乘数
     * @return 积
     */
    public IntegerNumber mult(int num){
        int inputSign = POSITIVE;
        if(num < 0){
            inputSign = NEGATIVE;
            num = -num;
        }
        int[] result = PositiveIntegerUtil.multiply(this.values, this.highPos, new int[]{ num }, 0);
        return new IntegerNumber((byte) (this.sign ^ inputSign), result);
    }

    /**
     * 除法
     * @param num 除数
     * @return 商
     */
    public IntegerNumber div(int num){
        return this.div(num, null);
    }

    /**
     * 除法
     * @param num 除数
     * @param remainder 余数
     * @return 商
     */
    public IntegerNumber div(int num, Remainder remainder){
        if(num == 0){
            throw new DivideByZeroException();
        }
        int inputSign = POSITIVE;
        if(num < 0){
            num = -num;
            inputSign = NEGATIVE;
        }
        int[][] result = PositiveIntegerUtil.divide(this.values, this.highPos, new int[]{ num }, 0);
        if(remainder != null){
            remainder.value = new IntegerNumber(POSITIVE, result[1]);
        }
        return new IntegerNumber((byte) (this.sign ^ inputSign), result[0]);
    }

    /**
     * 除法
     * @param num 除数
     * @param remainder 余数持有者, 如果该对象不为null, 运算结果中的余数会被放入该对象中
     * @return 商
     */
    public IntegerNumber div(IntegerNumber num, Remainder remainder){
        if(num.values.length == 1 && num.values[0] == 0){
            throw new DivideByZeroException();
        }
        int[][] result = PositiveIntegerUtil.divide(this.values, this.highPos, num.values, num.highPos);
        if(remainder != null){
            remainder.value = new IntegerNumber(POSITIVE, result[1]);
        }
        return new IntegerNumber((byte) (this.sign ^ num.sign), result[0]);
    }

    /**
     * 除法
     * @param num 除数
     * @return 商(余数会被舍去)
     */
    public IntegerNumber div(IntegerNumber num){
        return this.div(num, null);
    }

    /**
     * 获取该数的相反数
     * @return
     */
    public IntegerNumber not() {
        return new IntegerNumber(1 ^ this.sign, this.values);
    }

    /**
     * 求两数的最大公约数
     * @param num 参与运算的另一个数
     * @return 最大公约数
     */
    public IntegerNumber gcd(IntegerNumber num){
        return new IntegerNumber(POSITIVE, PositiveIntegerUtil.gcd(this.values, this.highPos, num.values, num.highPos));
    }

    /**
     * 判断是否是奇数
     * @return true - 奇数; false - 偶数
     */
    public boolean isOdd(){
        return PositiveIntegerUtil.isOdd(this.values);
    }

    /**
     * 求一个数的绝对值
     * @return 绝对值
     */
    public IntegerNumber abs() {
        if(this.sign == NEGATIVE){
            return new IntegerNumber(POSITIVE, this.values);
        }else{
            return this;
        }
    }

    /**
     * int类型转IntegerNumber
     * @param number 待转int
     * @return 转换结果
     */
    public static IntegerNumber valueOf(int number){
        if(number == 0){
            return ZERO;
        }else if(number == 1) {
            return ONE;
        }else if(number == -1){
            return NEGATIVE_ONE;
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

            return new IntegerNumber(sign, PositiveIntegerUtil.convertToNumberArray(absNum));
        }
    }

    /**
     * string解析为IntegerNumber
     * @param number 待解析的数字
     * @return 解析结果, 如果输入为空或者不是以数字开头, 将会报错
     */
    public static IntegerNumber valueOf(String number){
        number = checkAndFixNumber(number);
        if("0".equals(number) || "-0".equals(number)){
            return ZERO;
        }else if("1".equals(number)){
            return ONE;
        }else{
            int sign = POSITIVE;
            String numStr = number;
            if(number.startsWith("-")){
                sign = NEGATIVE;
                numStr = number.substring(1);
            }
            if(numStr.indexOf(SCIENTIFIC_MARK) > 0){
                return new IntegerNumber(sign, parseScientificNotation(numStr));
            }else{
                return new IntegerNumber(sign, numStr);
            }
        }
    }

    /**
     * 校验以及修正数字字符串
     * @param number
     * @return
     */
    private static String checkAndFixNumber(String number){
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
     * 获取当前值的正负
     * @return
     */
    public int getSign(){
        return this.sign;
    }

    public int compareTo(int num){
        if(num == 0){
            return this.values[this.highPos] - this.sign << 30;
        }else if(num < 0 == (this.sign == NEGATIVE) && this.highPos < 2){
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

    @Override
    public int compareTo(IntegerNumber o) {
        if(this.sign == o.sign){    // 同号
            if(this.sign == POSITIVE){
                return PositiveIntegerUtil.compare(this.values, this.highPos, o.values, o.highPos);
            }else{
                return -PositiveIntegerUtil.compare(this.values, this.highPos, o.values, o.highPos);
            }
        }else{  // 异号
            if(this.sign == POSITIVE){
                return 1;
            }else{
                return -1;
            }
        }
    }

    @Override
    public boolean equals(Object o){
        if(o == this){ return true; }
        if(o == null || o.getClass() != IntegerNumber.class){
            return false;
        }
        IntegerNumber that = (IntegerNumber) o;
        return this.sign == that.sign && PositiveIntegerUtil.compare(this.values, this.highPos, that.values, that.highPos) == 0;
    }

    @Override
    public int hashCode(){
        return (this.sign << 31) | this.values[this.highPos];
    }

    @Override
    public String decimal(int scale, NumberRoundingMode roundingMode, boolean fillWithZero) {
        return this.decimal(scale, roundingMode, fillWithZero, MAX_ENTIRELY_DISPLAY_POS);
    }

    private String decimal(int scale, NumberRoundingMode roundingMode, boolean fillWithZero, int scientificThreshold) {
        if(this.highPos > scientificThreshold){
            return this.scientificNotation(scale, roundingMode, fillWithZero);
        }else{
            return this.toIntegerString();
        }
    }

    private String toIntegerString(){
        StringBuilder sb = new StringBuilder();
        if(this.sign == NEGATIVE){ sb.append('-'); }
        sb.append(values[highPos]);
        for(int i = highPos - 1; i >= 0; i--){
            this.appendToStringBuilder(sb, values[i]);
        }
        return sb.toString();
    }

    @Override
    public String scientificNotation(int scale, NumberRoundingMode roundingMode, boolean fillWithZero) {
        long n = ((long)this.highPos) * PositiveIntegerUtil.SINGLE_ELEMENT_LEN;
        int hVal = this.values[this.highPos];
        StringBuilder dotAfterBuilder = new StringBuilder();
        while(hVal >= 10){
            n++;
            dotAfterBuilder.insert(0, Integer.toString(hVal % 10));
            hVal /= 10;
        }
        String afterDotStr = "";
        for(int i = this.highPos - 1; i >= 0; i--){
            this.appendToStringBuilder(dotAfterBuilder, this.values[i]);
            if(scale + 1 < dotAfterBuilder.length()){
                break;
            }
        }
        int realScale = scale;
        if(scale > dotAfterBuilder.length()){
            realScale = dotAfterBuilder.length();
        }
        afterDotStr = dotAfterBuilder.substring(0, realScale);
        int current = realScale == 0 ? hVal : dotAfterBuilder.charAt(realScale - 1) - '0';
        int nextNum = (dotAfterBuilder.length() > realScale) ? (dotAfterBuilder.charAt(realScale) - '0') : 0;
        int next2 = (dotAfterBuilder.length() > realScale + 1) ? (dotAfterBuilder.charAt(realScale + 1) - '0') : 0;
        int carry = roundingMode.round(current, nextNum, next2);

        char[] chars = afterDotStr.toCharArray();
        int unZero = -1;
        for(int i = chars.length - 1; i >= 0; i--){
            if(carry == 0){
                if(unZero != -1){ break; }
                if(chars[i] != '0'){
                    unZero = i;
                }
            }else{
                int c = chars[i] - '0' + carry;
                char numCh = chars[i] = (char)(c % 10 + '0');
                if(unZero == -1 && numCh != '0'){
                    unZero = i;
                }
                carry = c / 10;
            }
        }

        afterDotStr = String.valueOf(chars, 0, unZero + 1);

        if(carry > 0){
            hVal += carry;
            if(hVal >= 10){
                afterDotStr = hVal % 10 + afterDotStr;
                hVal = hVal / 10;
                if(afterDotStr.length() > scale){
                    afterDotStr = afterDotStr.substring(0, scale);
                }
            }
        }

        
        if(fillWithZero && scale > afterDotStr.length()){
            afterDotStr = StringUtils.rightFill(afterDotStr, '0', scale - afterDotStr.length());
        }

        String signMark = this.sign == POSITIVE ? "" : "-";
        if("".equals(afterDotStr)){
            return signMark + hVal + SCIENTIFIC_MARK + n;
        }else{
            return signMark + hVal + "." + afterDotStr + SCIENTIFIC_MARK + n;
        }
    }

    private String literal;

    private static final int MAX_ENTIRELY_DISPLAY_POS = 1000;

    @Override
    public String toString() {
        /*
         * 如果采用科学计数法, 则保留10位小数, 并四舍五入
         */
        if(literal == null){
            literal = this.decimal(10, NumberRoundingMode.HALF_UP, false, 20);
        }
        return literal;
    }

    private static final String FILL_ELEMENT = StringUtils.rightFill("", '0', PositiveIntegerUtil.SINGLE_ELEMENT_LEN);

    /**
     * 将一位数追加至StringBuilder中
     * @param sb 需要追加的目标
     * @param bitVal 需要追加的数
     */
    private void appendToStringBuilder(StringBuilder sb, int bitVal){
        int val = bitVal;
        if(val == 0){
            sb.append(FILL_ELEMENT);
        }else {
            while(val < PositiveIntegerUtil.SCALE / 10){
                sb.append(0);
                val *= 10;
            }
            sb.append(bitVal);
        }
    }

    private static final char SCIENTIFIC_MARK = 'E';

    /**
     * 解析科学计数法(只支持正整数)
     * 1.2345E16
     * @param origin 源字符串
     * @return 解析结果
     */
    private static int[] parseScientificNotation(final String origin){
        String originStr = origin;
        int dotPos = origin.indexOf('.');
        int ePos = origin.indexOf(SCIENTIFIC_MARK);

        if(ePos < 0 || ePos < dotPos){
            throw new IllegalArgumentException("number format incorrect for scientific notation : " + origin);
        }
        if(ePos == origin.length() - 1){    // 如果'E'是结尾, 则视为后面省略了1
            originStr = origin + "1";
        }

        String originEStr = originStr.substring(ePos + 1);
        int[] originE = generateVal(originEStr);

        int[] numArrAfterDot;
        
        String valStr;
        if(dotPos < 0){
            numArrAfterDot = new int[0];
            valStr = originStr.substring(0, ePos);
        }else{
            String valAfterDot = originStr.substring(dotPos + 1, ePos);
            int lenAfterDot = valAfterDot.length();
            numArrAfterDot = PositiveIntegerUtil.convertToNumberArray(lenAfterDot);
            if(PositiveIntegerUtil.compare(numArrAfterDot, numArrAfterDot.length - 1, originE, originE.length - 1) > 0){    // 防止转化之后是小数
                throw new IllegalArgumentException("can't parse with scientific notation for integer number : " + origin);
            }
            valStr = originStr.substring(0, dotPos) + valAfterDot;
        }

        int[] val = generateVal(valStr);
        int[] e = PositiveIntegerUtil.subtract(originE, originE.length - 1, numArrAfterDot, numArrAfterDot.length - 1);
        return PositiveIntegerUtil.decLeftShift(val, val.length - 1, e, PositiveIntegerUtil.highPos(e));
    }

    /**
     * 余数
     */
    public static class Remainder{
        private IntegerNumber value;
        public IntegerNumber getValue() {
            return value;
        }
    }

    
}
