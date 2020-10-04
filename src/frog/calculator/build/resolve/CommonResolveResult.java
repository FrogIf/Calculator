package frog.calculator.build.resolve;

import frog.calculator.express.IExpression;

public class CommonResolveResult implements IResolveResult {

    private IExpression expression;

    private int offset = -1;

    private boolean success = false;

    @Override
    public IExpression getExpression() {
        return this.expression;
    }

    void setExpression(IExpression expression){
        this.expression = expression;
        this.success = true;
    }

    @Override
    public int offset() {
        if(offset == -1){
            offset = this.expression == null ? 0 : this.expression.symbol().length();
        }
        return offset;
    }

    @Override
    public boolean success() {
        return this.success;
    }

}
