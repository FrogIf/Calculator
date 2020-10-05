package frog.calculator;

public interface ICalculatorConfigure {

    ICalculatorManager getCalculatorManager();

    /**
     * get value's output precision, it means '小数点后保留几位'
     */
    int getPrecision();

    /**
     * 指示, 输出小数还是分数
     * @return true: 小数, false: 分数
     */
    boolean outputDecimal();
}
