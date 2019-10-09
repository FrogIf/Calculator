package frog.calculator.operator.common;

import frog.calculator.connect.ICalculatorSession;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.express.separator.SeparatorExpression;
import frog.calculator.math.BaseNumber;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.space.ISpace;

public class DeclareOperator extends AbstractOperator {
    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        IExpression expression = exp.nextChild();

        ISpace result = expression.interpret();

        if(expression instanceof SeparatorExpression){
            SeparatorExpression se = (SeparatorExpression) expression;
            IExpression variable = se.getLeft();

            IExpressionContext context = expression.getContext();
            ICalculatorSession session = context.getSession();
            session.addSessionVariable(variable);
        }

        return result;
    }
}
