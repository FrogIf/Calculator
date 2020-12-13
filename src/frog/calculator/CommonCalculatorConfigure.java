package frog.calculator;

public class CommonCalculatorConfigure implements ICalculatorConfigure {

    private static final ICalculatorManager DEFAULT_CALCULATOR_MANAGER = new DefaultCalculatorManager();

    private ICalculatorManager calculatorManager = DEFAULT_CALCULATOR_MANAGER;

    public ICalculatorManager getCalculatorManager() {
        return calculatorManager;
    }

    public void setCalculatorManager(ICalculatorManager calculatorManager) {
        this.calculatorManager = calculatorManager;
    }
}
