package frog.calculator;

import frog.calculator.connect.DefaultSessionFactory;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.connect.ISessionFactory;
import frog.calculator.explain.DefaultExplainManager;
import frog.calculator.explain.IExplainManager;

public class DefaultCalculatorManager implements ICalculatorManager {

    private IExplainManager manager = new DefaultExplainManager();

    private ISessionFactory sessionFactory = new DefaultSessionFactory();

    private ICalculatorConfigure calculatorConfigure;

    public DefaultCalculatorManager(ICalculatorConfigure configure) {
        this.calculatorConfigure = configure;
    }

    @Override
    public ICalculatorSession getSession() {
        return sessionFactory.createSession(this);
    }

    @Override
    public IExplainManager getExplainManager() {
        return this.manager;
    }

    @Override
    public ICalculatorContext createCalculatorContext() {
        return new CommonCalculatorContext();
    }

    @Override
    public ICalculatorConfigure getConfigure() {
        return this.calculatorConfigure;
    }
}
