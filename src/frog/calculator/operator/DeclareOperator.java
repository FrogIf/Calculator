package frog.calculator.operator;

import frog.calculator.connect.ICalculatorSession;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.express.separator.SeparatorExpression;

public class DeclareOperator implements IOperator {
    @Override
    public IExpression operate(String symbol, IExpressionContext context, IExpression[] expressions) {
        IExpression exp = expressions[0];

        IExpression result = exp.interpret();

        if(exp instanceof SeparatorExpression){
            SeparatorExpression se = (SeparatorExpression) exp;
            IExpression variable = se.getLeft();

            ICalculatorSession session = context.getSession();
            session.addSessionVariable(variable);
        }

        return result;
    }
}
