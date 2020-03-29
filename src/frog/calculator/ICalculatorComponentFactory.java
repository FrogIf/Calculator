package frog.calculator;

public interface ICalculatorComponentFactory {

    ICalculatorManager createCalculatorManager(ICalculatorConfigure configure);

}
