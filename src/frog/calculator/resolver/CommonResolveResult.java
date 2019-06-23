package frog.calculator.resolver;

import frog.calculator.expression.IExpression;

public class CommonResolveResult extends AResolveResult {

    private IExpression expression;

    private int endIndex;

    @Override
    public IExpression getExpression() {
        return this.expression;
    }

    @Override
    public int getEndIndex() {
        return this.endIndex;
    }

    @Override
    void setEndIndex(int index) {
        this.endIndex = index;
    }

    @Override
    void setExpression(IExpression expression) {
        this.expression = expression;
    }
}
