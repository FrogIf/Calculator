package sch.frog.calculator.number;

/**
 * 该类仅供包内使用
 */
class InnerNumberUtil {

    private InnerNumberUtil(){
        // do nothing
    }

    /**
     * 科学计数法转换
     * @param number 待转换的数字
     * @param scale 保留小数后的位数
     * @param roundingMode 舍入方式
     * @param primaryN 初始指数
     * @return 转换后的结果
     */
    static String scientificNotationTransfer(String number, int scale, NumberRoundingMode roundingMode, long primaryN){
        long n = primaryN;
        int sign = number.startsWith("-") ? 1 : 0;

        int dotPos = number.indexOf('.');
        int len = number.length();
        if(dotPos < 0){
            if(len - sign > 1){
                n += len - 1 - sign;
                number = number.substring(0, 1 + sign) + "." + number.substring(1 + sign);
            }
        }else if(dotPos - sign > 1){    // 说明整数部分位数超过1位
            n += dotPos - sign - 1;
            number = number.substring(0, 1 + sign) + "." + number.substring(1 + sign, dotPos) + number.substring(dotPos + 1);
        }

        number = NumberRoundingMode.roundOff(number, scale, roundingMode);
        // 如果舍入之后, 产生进位, 使得整数部分不是只有一位数字, 则需要重新调整
        while((dotPos = number.indexOf('.')) - sign != 1){
            String afterDotStr;
            if(dotPos == -1){
                if(number.length() - sign == 1){ break; } // 是一个只有一位的整数
                afterDotStr = number.substring(1 + sign);
                n += number.length() - sign - 1;
            }else{
                afterDotStr = number.substring(1 + sign, dotPos) + number.substring(dotPos + 1);
                n += dotPos - 1 - sign;
            }
            number = NumberRoundingMode.roundOff(number.substring(0, 1 + sign) + "." + afterDotStr, scale, roundingMode);
        }
        return number + NumberConstant.SCIENTIFIC_MARK + n;
    }
    
}
