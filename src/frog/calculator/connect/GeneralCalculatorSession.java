package frog.calculator.connect;

import frog.calculator.compile.semantic.IValue;
import frog.calculator.util.IComparator;
import frog.calculator.util.collection.IMap;
import frog.calculator.util.collection.TreeMap;

public class GeneralCalculatorSession implements ICalculatorSession {

    private final IMap<String, IValue> variableMap = new TreeMap<>(IComparator.STRING_DEFAULT_COMPARATOR);

    @Override
    public IValue getVariable(String name) {
        return variableMap.get(name);
    }

    @Override
    public void addVariable(String name, IValue value) {
        variableMap.put(name, value);
    }
}
