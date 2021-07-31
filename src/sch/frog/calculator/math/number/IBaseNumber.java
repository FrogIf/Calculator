package sch.frog.calculator.math.number;

public interface IBaseNumber {

    /**
     * 设置数值范围
     * @param scale 保留的小数位数
     * @param roundingMode 保留精度
     */
    void setScale(int scale, NumberRoundingMode roundingMode, boolean fillWithZero);

    /**
     * 设置数值范围, 小数为不足指定位数, 则舍去
     * @param scale 保留的小数位数
     * @param roundingMode 保留精度
     */
    void setScale(int scale, NumberRoundingMode roundingMode);

}
