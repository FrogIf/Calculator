package io.github.frogif.calculator.runtime;

import io.github.frogif.calculator.compile.semantic.result.IValue;
import io.github.frogif.calculator.util.IComparator;
import io.github.frogif.calculator.util.collection.IMap;
import io.github.frogif.calculator.util.collection.TreeMap;

public class GeneralCalculatorSession implements ICalculatorSession {

    private RuntimeConfiguration runConfiguration = new RuntimeConfiguration();

    private final IMap<String, IValue> variableMap = new TreeMap<>(IComparator.STRING_DEFAULT_COMPARATOR);

    @Override
    public IValue getVariable(String name) {
        return variableMap.get(name);
    }

    @Override
    public void addVariable(String name, IValue value) {
        variableMap.put(name, value);
    }

    @Override
    public RuntimeConfiguration getRuntimeConfiguration() {
        return this.runConfiguration;
    }

    @Override
    public void setRuntimeConfiguration(RuntimeConfiguration configuration) {
        this.runConfiguration = configuration;
    }
}
