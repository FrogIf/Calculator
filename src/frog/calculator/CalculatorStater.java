package frog.calculator;

public class CalculatorStater {

    public static Calculator start(){
        CommonCalculatorConfigure configure = new CommonCalculatorConfigure();

        // 设置命令持有者工厂
        configure.setCalculatorComponentFactory(new DefaultCalculatorComponentFactory());

        return new Calculator(configure);
    }

}