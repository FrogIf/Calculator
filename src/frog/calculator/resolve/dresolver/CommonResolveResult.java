package frog.calculator.resolve.dresolver;

import frog.calculator.express.IExpression;

public class CommonResolveResult extends AResolveResult {

    private IExpression expression;

    private int endIndex;

    private String symbol;

    @Override
    public IExpression getExpression() {
        return this.expression;
    }

    @Override
    public int getEndIndex() {
        return this.endIndex;
    }

    @Override
    public String getSymbol() {
        return this.symbol;
    }

    @Override
    void setEndIndex(int index) {
        this.endIndex = index;
    }

    @Override
    void setExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
