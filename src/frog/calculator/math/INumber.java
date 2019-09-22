package frog.calculator.math;

public interface INumber extends Comparable<INumber>{

    byte getSign();

    INumber add(INumber num);

    INumber sub(INumber num);

    INumber mult(INumber num);

    INumber div(INumber num);

    /**
     * 转换为小数形式(四舍五入)
     * @param count 保留的小数位数
     * @return 返回转换结果
     */
    String toDecimal(int count);
}
