package frog.calculator;

public class CalculatorStater {

    public static Calculator start(){
        CommonCalculatorConfigure configure = new CommonCalculatorConfigure();
        configure.setPrecision(20);

        return new Calculator(new DefaultCalculatorManager(configure));
    }

}
