package frog.calculator;

public class CommonCalculatorConfigure implements ICalculatorConfigure {

    private ICalculatorComponentFactory calculatorComponentFactory;

    public void setCalculatorComponentFactory(ICalculatorComponentFactory calculatorComponentFactory){
        this.calculatorComponentFactory = calculatorComponentFactory;
    }

    @Override
    public ICalculatorComponentFactory getComponentFactory() {
        return this.calculatorComponentFactory;
    }


}
