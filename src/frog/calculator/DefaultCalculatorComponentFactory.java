package frog.calculator;

public class DefaultCalculatorComponentFactory implements ICalculatorComponentFactory {

    @Override
    public ICalculatorManager createCalculatorManager(ICalculatorConfigure configure) {
        return new DefaultCalculatorManager(configure);
    }

}
