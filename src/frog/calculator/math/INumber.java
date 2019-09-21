package frog.calculator.math;

public interface INumber extends Comparable<INumber>{

    byte getSign();

    INumber add(INumber number);

    INumber sub(INumber number);

    INumber mult(INumber number);

    INumber div(INumber number);

    /**
     * 转换为小数形式(四舍五入)
     * @param count 保留的小数位数
     * @return 返回转换结果
     */
    String toDecimal(int count);
}
