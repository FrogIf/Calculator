package frog.calculator.connect;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.util.IComparator;
import frog.calculator.util.collection.IMap;
import frog.calculator.util.collection.TreeMap;

public class GeneralCalculatorSession implements ICalculatorSession {

    private final IMap<String, ISyntaxNode> variableMap = new TreeMap<>(new IComparator<String>(){
        @Override
        public int compare(String a, String b) {
            return a.compareTo(b);
        }
    });

    @Override
    public void addVariable(ISyntaxNode node) {
        variableMap.put(node.word(), node);
    }

    @Override
    public ISyntaxNode getVariable(String name) {
        return variableMap.get(name);
    }
}
