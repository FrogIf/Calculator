package frog.calculator.connect;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.util.collection.IMap;
import frog.calculator.util.collection.StringTreeMap;

public class GeneralCalculatorSession implements ICalculatorSession {

    IMap<String, ISyntaxNode> variableMap = new StringTreeMap<>();

    @Override
    public void addVariable(ISyntaxNode node) {

    }

    @Override
    public ISyntaxNode getVariable(String name) {
        return null;
    }
}
