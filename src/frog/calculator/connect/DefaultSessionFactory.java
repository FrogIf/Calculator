package frog.calculator.connect;

public class DefaultSessionFactory implements ISessionFactory {
    @Override
    public ICalculatorSession createSession() {
        return new DefaultCalculatorSession();
    }
}
