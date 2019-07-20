package frog.calculator.resolver.resolve.factory;

import frog.calculator.express.IExpression;

public interface ICustomFunctionExpressionFactory {
    /**
     * 创建空白的变量表达式
     * @return
     */
    IExpression createBlankCustomFunctionExpression();
}
