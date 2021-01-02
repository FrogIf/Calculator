package frog.math.number;

/**
 * 数字
 */
public interface INumber {

    /**
     * 设置数值范围
     * @param scale 保留的小数位数
     * @param roundingMode 保留精度
     * @param fillWithZero 小数位不足, 是否填充0: true - 填充, false - 不填充
     */
    void setScale(int scale, NumberRoundingMode roundingMode, boolean fillWithZero);
    
}
