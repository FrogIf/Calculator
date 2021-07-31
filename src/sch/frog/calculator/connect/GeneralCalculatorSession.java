package sch.frog.calculator.connect;

import sch.frog.calculator.compile.semantic.result.IValue;
import sch.frog.calculator.util.IComparator;
import sch.frog.calculator.util.collection.IMap;
import sch.frog.calculator.util.collection.TreeMap;

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
