package io.github.frogif.calculator.runtime;

import io.github.frogif.calculator.compile.semantic.result.IValue;

/**
 * 计算器会话<br/>
 * 不同会话之间是完全数据隔离的, 每一次计算都需要使用会话<br/>
 * 单一会话是线程不安全的, 会话之间是线程安全的
 */
public interface ICalculatorSession {

    void addVariable(String name, IValue value);

    IValue getVariable(String name);

    RuntimeConfiguration getRuntimeConfiguration();

    void setRuntimeConfiguration(RuntimeConfiguration configuration);

}
