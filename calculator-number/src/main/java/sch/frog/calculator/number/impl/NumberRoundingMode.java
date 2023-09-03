package sch.frog.calculator.number.impl;

import sch.frog.calculator.number.util.OutObject;
import sch.frog.calculator.number.util.StrUtils;

/**
 * 舍入方式
 */
public enum NumberRoundingMode {

    /**
     * 向远离0的方向舍入
     */
    UP(new RoundingPolicy(){
        @Override
        public int round(boolean positive, int current, int next, boolean discard) {
            return discard ? 1 : 0;
        }

        @Override
        public int extraDecBitCount() {
            return 0;
        }

    }), 
    /**
     * 向接近0的方向舍入
     */
    DOWN(new RoundingPolicy(){
        @Override
        public int round(boolean positive, int current, int next, boolean discard) {
            return 0;
        }

        @Override
        public int extraDecBitCount() {
            return 0;
        }

    }),

    /**
     * 向上取整
     */
    CEILING(new RoundingPolicy(){

        @Override
        public int round(boolean positive, int current, int next, boolean discard) {
            return (discard && positive) ? 1 : 0;
        }

        @Override
        public int extraDecBitCount() {
            return 0;
        }

    }),
    /**
     * 向下取整
     */
    FLOOR(new RoundingPolicy(){

        @Override
        public int round(boolean positive, int current, int next, boolean discard) {
            return (discard && !positive) ? 1 : 0;
        }

        @Override
        public int extraDecBitCount() {
            return 0;
        }

    }),
    /**
     * 四舍五入
     */
    HALF_UP(new RoundingPolicy(){

        @Override
        public int round(boolean positive, int current, int next, boolean discard) {
            return next > 4 ? 1 : 0;
        }

        @Override
        public int extraDecBitCount() {
            return 1;
        }
    }),
    /**
     * 五舍六入
     */
    HALF_DOWN(new RoundingPolicy(){

        @Override
        public int round(boolean positive, int current, int next, boolean discard) {
            return (next > 5 || (next == 5 && discard)) ? 1 : 0;
        }

        @Override
        public int extraDecBitCount() {
            return 1;
        }
    }),
    /**
     * 银行家舍入
     * 1. 四舍六入
     * 2. 五后非0, 进一
     * 3. 五后为0:
     *      -- 五前为偶, 舍去
     *      -- 五前为奇, 进一
     */
    HALF_EVEN(new RoundingPolicy(){

        @Override
        public int round(boolean positive, int current, int next, boolean discard) {
            int n1 = next < 10 ? 0 : (next / 10);
            int n2 = next % 10;
            if(n1 < 5){
                return 0;
            }else if(n1 > 5){
                return 1;
            }else{
                if(n2 > 0 || discard){
                    return 1;
                }else{
                    return current % 2;
                }
            }
        }

        @Override
        public int extraDecBitCount() {
            return 2;
        }
    });

    private final RoundingPolicy policy;

    NumberRoundingMode(RoundingPolicy policy){
        this.policy = policy;
    }

    /**
     * 舍入
     * @param positive 整个待舍入的数字是否是正数: true - 是, false - 否
     * @param current 舍入之后, 保留的最后一位小数的当前值
     * @param next 舍入时, 参与判断的数字
     * @param discard 除去保留数字后, 是否存在多余的小数被丢弃
     * @return 按照该策略执行舍入之后, 产生的进位
     */
    private int round(boolean positive, int current, int next, boolean discard){
        return policy.round(positive, current, next, discard);
    }

    /**
     * 额外需要的十进制位个数
     */
    public int extraDecBitCount(){
        return policy.extraDecBitCount();
    }
    /**
     * 舍入策略
     */
    private interface RoundingPolicy{
        /**
         * 舍入
         * @param positive 整个待舍入的数字是否是正数: true - 是, false - 否
         * @param current 舍入之后, 保留的最后一位小数的当前值
         * @param next 舍入时, 参与判断的数字
         * @param discard 除去保留数字后, 是否存在多余的小数被丢弃
         * @return 按照该策略执行舍入之后, 产生的进位
         */
        int round(boolean positive, int current, int next, boolean discard);

        /**
         * 执行舍入时, @param next 对应10进制中的几位
         * @return 返回需要的十进制位
         */
        int extraDecBitCount();
    }

    private static final String[] SIGN_MARK_ARRAY = {"", "-"};

    /**
     * 对字符串数字进行舍入, 返回舍入后的结果
     */
    public static String roundOff(String number, int scale, NumberRoundingMode roundingMode){
        if(scale < 0){
            throw new IllegalArgumentException("scale must be positive, but is " + scale);
        }

        OutObject<Boolean> discard = new OutObject<>();
        number = normalizeNumberForRoundOff(number, scale, roundingMode, discard);
        
        char[] chars = number.toCharArray();
        
        // 判断正负
        final int start = chars[0] == '-' ? 1 : 0;

        /*
         * 定位小数点位置, 如果不存在小数点, 虚出小数点位置
         * 这里没有直接返回, 是因为即使不存在小数位, 也说不准roundingMode的行为会导致整数部分是否存在进位
         */
        int len = chars.length;
        int extraDecBitCount = roundingMode.extraDecBitCount();

        // 获取需要执行舍入的位的前一位
        int current = 0;
        int edge = len - 1 - extraDecBitCount;  // 舍入后, 保留部分的最大索引位置
        int skip = 0;
        if(chars[edge] == '.'){
            edge--;
            skip = 1;
        }
        current = chars[edge] - '0';

        // 确定roundingMode执行舍入需要判断的数
        int next = 0;
        for(int i = 0; i < extraDecBitCount; i++){
            int p = edge + skip + i + 1;
            next = next * 10 + ((len > p) ? (chars[p] - '0') : 0);
        }
        int carry = roundingMode.round(start == 0, current, next, Boolean.TRUE.equals(discard.value));  // 执行舍入

        // 执行舍入及进位
        int i = edge;
        boolean isZero = true; // 记录是否全为0, 如果全为0, 则符号位需要去掉
        for(; i >= start && carry != 0; i--){
            char ch = chars[i];
            if(ch != '.'){
                int c = ch - '0' + carry;
                chars[i] = (char)(c % 10 + '0');
                carry = c / 10;
                isZero = isZero && c == 0;
            }
        }

        if(carry > 0){
            return SIGN_MARK_ARRAY[start] + carry + String.valueOf(chars, start, edge - start + 1);
        }else{
            for(; isZero && i >= start; i--){
                char ch = chars[i];
                isZero = ch == '0' || ch == '.';
            }
            if(isZero){
                return String.valueOf(chars, start, edge - start + 1);
            }else{
                return String.valueOf(chars, 0, edge + 1);
            }
        }
    }

    /**
     * 修整数字, 使其方便进行舍入
     * 修整后的数字, 只存在舍入时需要的必要数字
     */
    private static String normalizeNumberForRoundOff(String number, int scale, NumberRoundingMode roundingMode, OutObject<Boolean> discard){
        int dotPos = number.indexOf('.');
        int len = number.length();
        int lackLen = (dotPos < 0 ? (scale + 1) : (scale - (len - dotPos - 1))) + roundingMode.extraDecBitCount();
        discard.value = false;
        if(lackLen > 0){    // 需要补位
            if(dotPos < 0){
                return StrUtils.rightFill(number + '.', '0', lackLen - 1);
            }else{
                return StrUtils.rightFill(number, '0', lackLen);
            }
        }else if(lackLen < 0){  // 需要截断
            for(int i = len + lackLen; i < len; i++){
                char ch = number.charAt(i);
                if(ch != '0'){
                    discard.value = true;
                    break;
                }
            }
            return number.substring(0, len + lackLen);
        }else{  // 直接返回
            return number;
        }
    }
}

