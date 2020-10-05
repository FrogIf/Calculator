package frog.calculator;

import frog.calculator.build.CommonBuildManager;
import frog.calculator.build.IBuildManager;
import frog.calculator.connect.DefaultSessionFactory;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.connect.ISessionFactory;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.UnmodifiableList;

public class DefaultCalculatorManager implements ICalculatorManager {

    private IBuildManager manager = new CommonBuildManager();

    private ISessionFactory sessionFactory = new DefaultSessionFactory();

    private IList<ICalculateListener> listeners = new ArrayList<>();

    public void setManager(IBuildManager manager) {
        this.manager = manager;
    }

    public void setSessionFactory(ISessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public ICalculatorSession createSession() {
        return sessionFactory.createSession(this);
    }

    @Override
    public IBuildManager getBuildManager() {
        return this.manager;
    }

    @Override
    public IList<ICalculateListener> getCalculatorListeners() {
        return new UnmodifiableList(listeners);
    }

    public void addCalculateListener(ICalculateListener listener){
        listeners.add(listener);
    }

}
