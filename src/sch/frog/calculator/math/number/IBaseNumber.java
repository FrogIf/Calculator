package sch.frog.calculator.math.number;

public interface IBaseNumber {

    /**
     * 转化为小数
     * @param scale 保留的小数位数
     * @param roundingMode 舍入方式
     * @param fillWithZero 小数不足位是否补0, true - 是, false - 否
     * @return 返回按指定精度截断的小数字面值
     */
    String decimal(int scale, NumberRoundingMode roundingMode, boolean fillWithZero);

    /**
     * 转化为小数, 小数为不足指定位数, 不填充0
     * @param scale 保留的小数位数
     * @param roundingMode 舍入方式
     * @return 返回按指定精度截断的小数字面值
     */
    String decimal(int scale, NumberRoundingMode roundingMode);

    /**
     * 转换为科学计数法
     * @param scale 保留小数位数
     * @param roundingMode 舍入方式
     * @param fillWithZero 小数不足位是否补0
     * @return 返回指定精度截断的科学计数法表示形式
     */
    String scientificNotation(int scale, NumberRoundingMode roundingMode, boolean fillWithZero);

    /**
     * 转换为科学计数法
     * @param scale 保留的小数位数
     * @param roundingMode 舍入方式
     * @return 返回指定精度截断的科学计数法表示形式
     */
    String scientificNotation(int scale, NumberRoundingMode roundingMode);

}
