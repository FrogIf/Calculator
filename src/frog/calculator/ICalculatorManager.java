package frog.calculator;

import frog.calculator.build.IExpressionBuilder;
import frog.calculator.build.resolve.IResolverResult;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.express.IExpression;

public interface ICalculatorManager {

    IResolverResult createResolverResult(IExpression expression);

    ICalculatorSession createCalculatorSession();

    IExpressionBuilder createExpressionBuilder(ICalculatorSession session);

    ICalculatorContext createCalculatorContext();
}
