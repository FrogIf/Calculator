package frog.calculator;

import frog.calculator.connect.ICalculatorSession;
import frog.calculator.explain.IExplainManager;

/**
 * 计算器管理器
 */
public interface ICalculatorManager {

    ICalculatorSession getSession();

    IExplainManager getExplainManager();

    ICalculatorContext createCalculatorContext();

    ICalculatorConfigure getConfigure();
}
