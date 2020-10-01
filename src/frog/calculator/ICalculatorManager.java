package frog.calculator;

import frog.calculator.connect.ICalculatorSession;
import frog.calculator.build.IExplainManager;

/**
 * 计算器管理器
 */
public interface ICalculatorManager {

    ICalculatorSession getSession();

    IExplainManager getExplainManager();

    ICalculatorContext createCalculatorContext();

    ICalculatorConfigure getConfigure();
}
