package io.github.frogif.calculator.facade;

import io.github.frogif.calculator.runtime.GeneralCalculatorSession;
import io.github.frogif.calculator.runtime.ICalculatorSession;

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
