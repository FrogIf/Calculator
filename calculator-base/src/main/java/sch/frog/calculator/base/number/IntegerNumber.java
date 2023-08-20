package sch.frog.calculator.base.number;

import sch.frog.calculator.base.exception.DivideByZeroException;
import sch.frog.calculator.base.util.StrUtils;

/**
 * 整数
 */
public final class IntegerNumber extends AbstractBaseNumber implements Comparable<IntegerNumber> {

    /**
     * 正
     */
    private static final int SIGN_POSITIVE = 0;

    /**
     * 负
     */
    private static final int SIGN_NEGATIVE = 1;

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
     * IntegerNumber与int值进行加/减运算
     * @param num 参与运算的数
     * @param operator 参与的运算, 与
     */
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

    /**
     * 加法
     * @param num 加数
     * @return 和
     */
    public IntegerNumber add(IntegerNumber num){
        return accumulation(this, num, SIGN_POSITIVE);
    }

    /**
     * 加法
     * @param num 加数
     * @return 和
     */
    public IntegerNumber add(int num){
        return accumulationOneWord(num, SIGN_POSITIVE);
    }

    /**
     * 减法
     * @param num 减数
     * @return 差
     */
    public IntegerNumber sub(IntegerNumber num){
        return accumulation(this, num, SIGN_NEGATIVE);
    }

    /**
     * 减法
     * @param num 减数
     * @return 差
     */
    public IntegerNumber sub(int num){
        return accumulationOneWord(num, SIGN_NEGATIVE);
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
        int inputSign = SIGN_POSITIVE;
        if(num < 0){
            inputSign = SIGN_NEGATIVE;
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
        int inputSign = SIGN_POSITIVE;
        if(num < 0){
            num = -num;
            inputSign = SIGN_NEGATIVE;
        }
        int[][] result = PositiveIntegerUtil.divide(this.values, this.highPos, new int[]{ num }, 0);
        if(remainder != null){
            remainder.value = new IntegerNumber(SIGN_POSITIVE, result[1]);
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
            remainder.value = new IntegerNumber(SIGN_POSITIVE, result[1]);
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
        return new IntegerNumber(SIGN_POSITIVE, PositiveIntegerUtil.gcd(this.values, this.highPos, num.values, num.highPos));
    }

    /**
     * 判断是否是奇数
     * @return true - 奇数; false - 偶数
     */
    public boolean isOdd(){
        return !PositiveIntegerUtil.isEven(this.values);
    }

    /**
     * 判断是否是偶数
     * @return true - 偶数; false - 奇数
     */
    public boolean isEven(){
        return PositiveIntegerUtil.isEven(this.values);
    }

    /**
     * 求一个数的绝对值
     * @return 绝对值
     */
    public IntegerNumber abs() {
        if(this.sign == SIGN_NEGATIVE){
            return new IntegerNumber(SIGN_POSITIVE, this.values);
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
                sign = SIGN_NEGATIVE;
            }else{
                absNum = number;
                sign = SIGN_POSITIVE;
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
        number = fixNumber(number);
        if("0".equals(number)){
            return ZERO;
        }else if("1".equals(number)){
            return ONE;
        }else{
            int sign = SIGN_POSITIVE;
            String numStr = number;
            if(number.startsWith("-")){
                sign = SIGN_NEGATIVE;
                numStr = number.substring(1);
            }
            int ePos = numStr.indexOf(NumberConstant.SCIENTIFIC_MARK);
            if(ePos > 0){
                return new IntegerNumber(sign, parseScientificNotation(numStr));
            }else{
                return new IntegerNumber(sign, numStr);
            }
        }
    }

    /**
     * 校验以及修正数字字符串
     */
    private static String fixNumber(String number){
        if(number == null){
            throw new IllegalArgumentException("input number is blank.");
        }else{
            number = number.trim();
            if(number.equals("") || number.equals("-")){
                throw new IllegalArgumentException("input number is blank.");
            }
        }
        int start = -1;
        char startCh = number.charAt(0);
        if(startCh == '0'){
            start = 0;
        }else if(startCh == '-'){
            start = 1;
        }

        if(start != -1){
            int pz = start;
            int ePos = -1;
            int len = number.length();
            for(int i = start; i < len; i++){
                char ch = number.charAt(i);
                if(ch == '0'){
                    pz++;
                }else{
                    if(ch == NumberConstant.SCIENTIFIC_MARK){
                        ePos = i;
                    }
                    break;
                }
            }
            if(pz >= len || ePos == pz){
                number = "0";
            }else{
                number = (start == 1 ? "-" : "") + number.substring(pz);
            }
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
     */
    public NumberSign getSign(){
        return this.sign == SIGN_NEGATIVE ? NumberSign.NEGATIVE : NumberSign.POSITIVE;
    }

    public int compareTo(int num){
        if(num == 0){
            return this.values[this.highPos] - (this.sign << 30);
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

    @Override
    public int compareTo(IntegerNumber o) {
        if(this.sign == o.sign){    // 同号
            if(this.sign == SIGN_POSITIVE){
                return PositiveIntegerUtil.compare(this.values, this.highPos, o.values, o.highPos);
            }else{
                return -PositiveIntegerUtil.compare(this.values, this.highPos, o.values, o.highPos);
            }
        }else{  // 异号
            if(this.sign == SIGN_POSITIVE){
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
    public String decimal(int scale, NumberRoundingMode roundingMode) {
        return this.decimal(scale, roundingMode, NumberConstant.SCIENTIFIC_THRESHOLD);
    }

    private String decimal(int scale, NumberRoundingMode roundingMode, int scientificThreshold) {
        if(this.highPos > scientificThreshold){
            return this.scientificNotation(scale, roundingMode);
        }else{
            return this.toPlainString();
        }
    }

    /**
     * 朴素输出, 不以科学计数法的方式输出
     */
    public String toPlainString(){
        StringBuilder sb = new StringBuilder();
        if(this.sign == SIGN_NEGATIVE){ sb.append('-'); }
        sb.append(values[highPos]);
        for(int i = highPos - 1; i >= 0; i--){
            this.appendToStringBuilder(sb, values[i]);
        }
        return sb.toString();
    }

    @Override
    public String scientificNotation(int scale, NumberRoundingMode roundingMode) {
        long n = ((long)this.highPos) * PositiveIntegerUtil.SINGLE_ELEMENT_LEN; // 科学计数法指数值

        // 确定小数点前面的数字, 并拼接小数点后面部分
        int hVal = this.values[this.highPos];
        StringBuilder dotAfterBuilder = new StringBuilder();
        while(hVal >= 10){
            n++;
            dotAfterBuilder.insert(0, Integer.toString(hVal % 10));
            hVal /= 10;
        }
        int extraDecBitCount = roundingMode.extraDecBitCount();
        int i = this.highPos - 1;
        while(i >= 0){
            this.appendToStringBuilder(dotAfterBuilder, this.values[i]);
            i--;
            if(dotAfterBuilder.length() >= scale + extraDecBitCount){
                break;
            }
        }

        for(; i >= 0; i--){
            if(this.values[i] != 0){
                dotAfterBuilder.append("1");
            }
        }

        // 执行舍入
        String number = (this.sign == SIGN_NEGATIVE ? "-" : "") + 
                hVal + (dotAfterBuilder.length() == 0 ? "" : ("." + dotAfterBuilder));
        
        return InnerNumberUtil.scientificNotationTransfer(number, scale, roundingMode, n);
    }

    private String literal;

    @Override
    public String toString() {
        /*
         * 如果采用科学计数法, 则保留10位小数, 并四舍五入
         */
        if(literal == null){
            literal = this.decimal(0, NumberRoundingMode.HALF_UP, 20);
        }
        return literal;
    }

    private static final String FILL_ELEMENT = StrUtils.rightFill("", '0', PositiveIntegerUtil.SINGLE_ELEMENT_LEN);

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

    /**
     * 解析科学计数法(只支持正整数)
     * 1.2345E16
     * @param origin 源字符串
     * @return 解析结果
     */
    private static int[] parseScientificNotation(final String origin){
        String originStr = origin;
        int dotPos = origin.indexOf('.');
        int ePos = origin.indexOf(NumberConstant.SCIENTIFIC_MARK);

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
