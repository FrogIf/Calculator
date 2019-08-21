package frog.calculator;

import frog.calculator.express.IExpression;

public interface IExpressionHolder {

    /**
     * 正号表达式
     * @return
     */
    IExpression getPlus();

    /**
     * 负号表达式
     * @return
     */
    IExpression getMinus();

    /**
     * 赋值符号
     * @return
     */
    IExpression getAssign();

    /**
     * 声明开始标识符
     * @return
     */
    IExpression getDeclareBegin();

    /**
     * 分割符
     * @return
     */
    IExpression getSeparator();

    /**
     * 容器开始符
     * @return
     */
    IExpression getFunArgStart();

    /**
     * 容器关闭符
     * @return
     */
    IExpression getFunArgEnd();

    /**
     * 获取内置可执行表达式
     * @return
     */
    IExpression[] getBuiltInExpression();

    IExpression getDelegate();

}
