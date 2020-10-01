package frog.calculator.build;

import frog.calculator.connect.ICalculatorSession;
import frog.calculator.exception.BuildException;
import frog.calculator.express.IExpression;

public interface IExpressionTreeBuilder {

    /**
     * 将指定的字符串解析为表达式树
     * @param expression 待解析的字符串
     * @return 解析结果
     * @throws BuildException 构建失败
     */
    IExpression build(char[] expression, ICalculatorSession session) throws BuildException;

}
