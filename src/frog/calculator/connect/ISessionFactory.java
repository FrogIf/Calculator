package frog.calculator.connect;

import frog.calculator.ICalculatorManager;

public interface ISessionFactory {
    ICalculatorSession createSession(ICalculatorManager manager);
}
