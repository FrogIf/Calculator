package frog.calculator.connect;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.util.collection.IMap;

public class GeneralCalculatorSession implements ICalculatorSession {

    IMap<String, ISyntaxNode> variableMap = null;

    @Override
    public void addVariable(ISyntaxNode node) {
        variableMap.put(node.word(), node);
    }

    @Override
    public ISyntaxNode getVariable(String name) {
        return variableMap.get(name);
    }
}
