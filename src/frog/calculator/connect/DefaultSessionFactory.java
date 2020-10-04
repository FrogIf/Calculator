package frog.calculator.connect;

import frog.calculator.ICalculatorManager;

public class DefaultSessionFactory implements ISessionFactory {
    @Override
    public ICalculatorSession createSession(ICalculatorManager manager) {
        return new DefaultCalculatorSession();
    }
}
