package sch.frog.calculator.facade;

import sch.frog.calculator.runtime.GeneralCalculatorSession;
import sch.frog.calculator.runtime.ICalculatorSession;

public class ExecuteSession {

    private final ICalculatorSession calculateSession;

    private final SessionConfiguration sessionConfiguration = new SessionConfiguration();

    public ExecuteSession(){
        this.calculateSession = new GeneralCalculatorSession();
    }

    public ICalculatorSession getCalculatorSession(){
        return this.calculateSession;
    }

    public SessionConfiguration getSessionConfiguration() {
        return sessionConfiguration;
    }
}
