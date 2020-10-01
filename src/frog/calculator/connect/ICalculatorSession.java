package frog.calculator.connect;

import frog.calculator.build.IVariableManager;

/**
 * 计算器会话<br/>
 * 不同会话之间是完全数据隔离的, 每一次计算都需要使用会话<br/>
 * 单一会话是线程不安全的, 会话之间是线程安全的
 */
public interface ICalculatorSession extends IVariableManager {


}
