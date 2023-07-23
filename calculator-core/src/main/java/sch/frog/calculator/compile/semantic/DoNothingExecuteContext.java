package sch.frog.calculator.compile.semantic;

import sch.frog.calculator.compile.semantic.result.IValue;

public class DoNothingExecuteContext implements IExecuteContext{
    @Override
    public void addVariable(String name, IValue value) {
        throw new UnsupportedOperationException("add variable unsupported");
    }

    @Override
    public IValue getVariable(String name) {
        throw new UnsupportedOperationException("get variable unsupported");
    }

    @Override
    public void createVariableStack() {
        throw new UnsupportedOperationException("createVariableStack unsupported");
    }

    @Override
    public void destoryTopStack() {
        throw new UnsupportedOperationException("destoryTopStack unsupported");
    }
}
