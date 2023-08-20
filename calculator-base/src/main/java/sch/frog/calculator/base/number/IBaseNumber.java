package sch.frog.calculator.base.number;

public interface IBaseNumber {

    /**
     * 转化为小数, 小数为不足指定位数, 不填充0
     * @param scale 保留的小数位数
     * @param roundingMode 舍入方式
     * @return 返回按指定精度截断的小数字面值
     */
    String decimal(int scale, NumberRoundingMode roundingMode);

    /**
     * 转换为科学计数法
     * @param scale 保留的小数位数
     * @param roundingMode 舍入方式
     * @return 返回指定精度截断的科学计数法表示形式
     */
    String scientificNotation(int scale, NumberRoundingMode roundingMode);

    /**
     * 获取数的正负
     */
    NumberSign getSign();

}
