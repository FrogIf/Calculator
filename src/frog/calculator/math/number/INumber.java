package frog.calculator.math.number;

public interface INumber {
    /**
     * 获取一个数的相反数
     * @return
     */
    INumber not();

    /**
     * 转换为小数形式(四舍五入)
     * @param precision 保留的小数位数
     * @return 返回转换结果
     */
    String toDecimal(int precision);
}
