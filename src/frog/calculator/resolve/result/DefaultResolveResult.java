package frog.calculator.resolve.result;

import frog.calculator.express.IExpression;
import frog.calculator.resolve.IResolveResult;

public class DefaultResolveResult implements IResolveResult {

    private IExpression expression;

    private int endIndex = -1;

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
    public void setEndIndex(int index) {
        if(this.endIndex > 0){
            throw new IllegalStateException("the endIndex can't be modify.");
        }
        this.endIndex = index;
    }

    @Override
    public void setExpression(IExpression expression) {
        if(this.expression != null){
            throw new IllegalStateException("the expression can't be modify.");
        }
        this.expression = expression;
    }

    @Override
    public void setSymbol(String symbol) {
        if(this.symbol != null){
            throw new IllegalStateException("the symbol can't be modify.");
        }
        this.symbol = symbol;
    }
}
