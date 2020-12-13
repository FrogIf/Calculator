package frog.calculator;

import frog.calculator.build.MathExpressionBuildManager;
import frog.calculator.build.IBuildManager;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.UnmodifiableList;

public class DefaultCalculatorManager implements ICalculatorManager {

    private IBuildManager manager = new MathExpressionBuildManager();

    private IList<ICalculateListener> listeners = new ArrayList<>();

    public void setManager(IBuildManager manager) {
        this.manager = manager;
    }

    @Override
    public IBuildManager getBuildManager() {
        return this.manager;
    }

    @Override
    public IList<ICalculateListener> getCalculatorListeners() {
        return new UnmodifiableList<>(listeners);
    }

    public void addCalculateListener(ICalculateListener listener){
        listeners.add(listener);
    }

}
