package frog.calculator;

public class CommonCalculatorConfigure implements ICalculatorConfigure {

    private ICalculatorComponentFactory calculatorComponentFactory;

    private int precision = 10;

    public void setCalculatorComponentFactory(ICalculatorComponentFactory calculatorComponentFactory){
        this.calculatorComponentFactory = calculatorComponentFactory;
    }

    @Override
    public ICalculatorComponentFactory getComponentFactory() {
        return this.calculatorComponentFactory;
    }

    @Override
    public int precision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }
}
