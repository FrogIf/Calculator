package frog.calculator.resolver;

import frog.calculator.express.IExpression;

public class DefaultResolveResult implements IResolverResult {

    private IExpression expression;

    private int offset = -1;

    @Override
    public IExpression getExpression() {
        return this.expression;
    }

    void setExpression(IExpression expression){
        this.expression = expression;
    }

    @Override
    public int offset() {
        if(offset == -1){
            offset = this.expression.symbol().length();
        }
        return offset;
    }

    @Override
    public void setOffset(int offset) {
        this.offset = offset;
    }


    @Override
    public DefaultResolveResult clone(){
        try {
            return (DefaultResolveResult) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
