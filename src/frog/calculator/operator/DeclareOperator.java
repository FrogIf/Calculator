package frog.calculator.operator;

import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.express.endpoint.MapExpression;
import frog.calculator.register.IRegister;

public class DeclareOperator implements IOperator {

    @Override
    public IExpression operate(String symbol, IExpressionContext context, IExpression... expressions) {
        IExpression variable = expressions[0];
        IExpression result = variable.interpret();
        IRegister userRegister = context.getSession().getUserRegister();

        MapExpression mapExpression = new MapExpression(variable.symbol(), result);
        userRegister.replace(variable.symbol(), mapExpression, mapExpression.getOperator());

        return result;
    }
}
