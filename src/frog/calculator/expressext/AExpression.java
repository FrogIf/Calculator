package frog.calculator.expressext;

public abstract class AExpression implements IExpression{

    public AExpression(String symbol) {
        this.symbol = symbol;
    }

    private String symbol;

    @Override
    public String symbol() {
        return this.symbol;
    }
}
