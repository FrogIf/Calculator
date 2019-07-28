package frog.calculator.express;

import frog.calculator.connect.ICalculatorSession;
import frog.calculator.util.LinkedList;

/**
 * 表达式上下文
 */
public interface IExpressionContext {

    /**
     * 该表达式树的变量池
     * @return
     */
    LinkedList<IExpression> getVariables();

    /**
     * 获取用户会话
     * @return
     */
    ICalculatorSession getSession();

    /**
     * 为当前上下文添加局部变量
     * @param expression
     */
    void addLocalVariables(IExpression expression);

    /**
     * 以当前上下文为原型创建新的上下文对象
     * @return
     */
    IExpressionContext newInstance();
}
