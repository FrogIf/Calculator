package frog.calculator.operator.common;

import frog.calculator.connect.ICalculatorSession;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.express.separator.SeparatorExpression;
import frog.calculator.math.INumber;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.operator.IOperator;
import frog.calculator.space.IPoint;
import frog.calculator.space.ISpace;

public class DeclareOperator extends AbstractOperator {
    @Override
    public ISpace<IPoint<INumber>> operate(IExpression exp) {
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
