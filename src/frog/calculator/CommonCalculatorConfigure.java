package frog.calculator;

public class CommonCalculatorConfigure implements ICalculatorConfigure {

    private int precision = 10;

    private boolean outputDecimal = true;

    private static final ICalculatorManager DEFAULT_CALCULATOR_MANAGER = new DefaultCalculatorManager();

    private ICalculatorManager calculatorManager = DEFAULT_CALCULATOR_MANAGER;

    public ICalculatorManager getCalculatorManager() {
        return calculatorManager;
    }

    public int getPrecision() {
        return precision;
    }

    @Override
    public boolean outputDecimal() {
        return outputDecimal;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public void setCalculatorManager(ICalculatorManager calculatorManager) {
        this.calculatorManager = calculatorManager;
    }

    public void setOutputDecimal(boolean outputDecimal) {
        this.outputDecimal = outputDecimal;
    }
}
