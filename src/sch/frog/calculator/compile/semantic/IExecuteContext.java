package sch.frog.calculator.compile.semantic;

import sch.frog.calculator.compile.semantic.result.IValue;

/**
 * 执行上下文
 */
public interface IExecuteContext {

    /**
     * 向上下文中添加新的变量
     * @param name 变量名
     * @param value 变量值
     */
    void addVariable(String name, IValue value);

    /**
     * 从上下文中获取变量
     * @param name 变量名
     * @return 变量值
     */
    IValue getVariable(String name);

    /**
     * 创建新的变量栈
     */
    void createVariableStack();

    /**
     * 销毁顶层变量栈, 如果不存在变量栈, 则不执行任何操作
     */
    void destoryTopStack();
    
}
