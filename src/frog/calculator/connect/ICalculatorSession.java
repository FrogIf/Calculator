package frog.calculator.connect;

import frog.calculator.express.IExpression;

public interface ICalculatorSession {

    /**
     * 添加会话变量
     * @param expression
     */
    void addVariable(IExpression expression);

    /**
     * 从会话变量中获取值
     * @param varName
     * @return
     */
    IExpression getVariable(String varName);

    /**
     * 从会话变量中获取值
     * @return
     */
    IExpression getVariable(char[] expChars, int startIndex);
}
