package frog.calculator.connect;

import frog.calculator.express.IExpression;

public interface ICalculatorSession {

    /**
     * 添加会话变量
     * @param expression
     */
    void addSessionVariable(IExpression expression);

    /**
     * 从会话变量中获取值
     * @param varName
     * @return
     */
    IExpression getSessionVariable(String varName);

    /**
     * 从会话变量中获取值
     * @return
     */
    IExpression getSessionVariable(char[] expChars, int startIndex);
}
