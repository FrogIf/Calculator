package frog.calculator;

import frog.calculator.build.IExpressionBuilder;
import frog.calculator.build.resolve.IResolverResult;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.express.IExpression;

/**
 * 计算器管理器
 */
public interface ICalculatorManager {

    /**
     * 将表达式包装为解析结果
     * @param expression 待包装的表达式
     * @return 解析结果
     */
    IResolverResult createResolverResult(IExpression expression);

    ICalculatorSession createCalculatorSession();

    IExpressionBuilder createExpressionBuilder(ICalculatorSession session);

    ICalculatorContext createCalculatorContext();
}
