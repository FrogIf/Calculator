package frog.calculator;

import frog.calculator.util.collection.IList;

/**
 * 计算器上下文
 */
public interface ICalculatorContext {

    void addCalculateListener(ICalculateListener calculateListener);

    IList<ICalculateListener> getCalculateListeners();
}
