package frog.calculator.express;

import frog.calculator.connect.ICalculatorSession;
import frog.calculator.util.LinkedList;

/**
 * 表达式上下文
 */
public interface IExpressionContext {

    /**
     * get local variable
     * @param varName variable name
     * @return variable's expression
     */
    IExpression getLocalVariable(String varName);

    /**
     * 为当前上下文添加局部变量
     * @param expression
     */
    void addLocalVariable(IExpression expression);

    /**
     * 获取用户会话
     * @return
     */
    ICalculatorSession getSession();

    /**
     * 以当前上下文为原型创建新的上下文对象
     * @return
     */
    IExpressionContext newInstance();
}
