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
     * 声明结束标识符
     * @return
     */
    IExpression getDelcareEnd();

    /**
     * 获取内置可执行表达式
     * @return
     */
    IExpression[] getBuiltInExpression();

    /**
     * 声明结构表达式
     * @return
     */
    IExpression[] getDeclareStruct();

}
