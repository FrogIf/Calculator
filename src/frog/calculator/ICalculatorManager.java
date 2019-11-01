package frog.calculator;

import frog.calculator.connect.ICalculatorSession;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.resolver.IResolverResult;

public interface ICalculatorManager {

    IResolverResult createResolverResult(IExpression expression);

    ICalculatorSession createCalculatorSession();

    IExpressionContext createExpressionContext(ICalculatorSession session);

}
