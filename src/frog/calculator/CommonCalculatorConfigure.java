package frog.calculator;

public class CommonCalculatorConfigure implements ICalculatorConfigure {

    private int precision = 10;

    @Override
    public int precision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }
}
