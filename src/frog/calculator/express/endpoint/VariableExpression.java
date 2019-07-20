package frog.calculator.express.endpoint;

import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;

public class VariableExpression extends EndPointExpression{

    private IExpressionContext context;

    public VariableExpression(String symbol) {
        super(symbol, null);
    }

    @Override
    public void setExpressionContext(IExpressionContext context) {
        this.context = context;
    }

    @Override
    public IExpression interpret() {
        IExpression[] variables = context.getVariables();
        for(IExpression variable : variables){
            if(variable.symbol().equals(this.symbol)){
                return variable.interpret();
            }
        }
        throw new IllegalArgumentException("undefine variables");
    }
}
