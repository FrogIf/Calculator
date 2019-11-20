package frog.calculator;

import frog.calculator.util.collection.IList;

public interface ICalculatorContext {

    void addCalculateListener(ICalculateListener calculateListener);

    IList<ICalculateListener> getCalculateListeners();
}
