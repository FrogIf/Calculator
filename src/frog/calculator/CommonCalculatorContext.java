package frog.calculator;

import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.LinkedList;
import frog.calculator.util.collection.UnmodifiableList;

public class CommonCalculatorContext implements ICalculatorContext {

    private IList<ICalculateListener> listener = new LinkedList<>();

    @Override
    public void addCalculateListener(ICalculateListener calculateListener) {
        if(calculateListener == null){
            throw new IllegalArgumentException("calculate listener is null.");
        }
        listener.add(calculateListener);
    }

    @Override
    public IList<ICalculateListener> getCalculateListeners() {
        return new UnmodifiableList<>(listener);
    }

}
