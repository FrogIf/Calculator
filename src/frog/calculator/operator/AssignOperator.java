package frog.calculator.operator;

import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.express.endpoint.VariableExpression;

public class AssignOperator implements IOperator {

    @Override
    public IExpression operate(String symbol, IExpressionContext context, IExpression[] expressions) {
        IExpression variable = expressions[0];
        IExpression value = expressions[1].interpret();

        if(variable instanceof VariableExpression){
            ((VariableExpression) variable).assign(value);
        }else{
            throw new IllegalArgumentException("can't support type.");
        }

        return value;
    }

}
