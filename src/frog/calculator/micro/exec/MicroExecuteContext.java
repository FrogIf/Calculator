package frog.calculator.micro.exec;

import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.util.IComparator;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.IMap;
import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.Stack;
import frog.calculator.util.collection.TreeMap;

public class MicroExecuteContext implements IExecuteContext {

    private IList<ComplexNumber> result;

    private final ICalculatorSession session;

    private final Stack<IMap<String, ISyntaxNode>> variableStack = new Stack<>();

    public MicroExecuteContext(ICalculatorSession session){
        this.session = session;
    }

    public IList<ComplexNumber> getResult() {
        return result;
    }

    public void setResult(IList<ComplexNumber> result) {
        this.result = result;
    }

    public void addVariable(ISyntaxNode node){
        if(variableStack.isEmpty()){
            session.addVariable(node);
        }else{
            variableStack.top().put(node.word(), node);
        }
    }

    public ISyntaxNode getVariable(String name){
        ISyntaxNode val = null;

        Iterator<IMap<String, ISyntaxNode>> itr = variableStack.invertedIterator();
        while(result == null && itr.hasNext()){
            IMap<String, ISyntaxNode> map = itr.next();
            val = map.get(name);
        }

        if(result == null){
            val = session.getVariable(name);
        }
        return val;
    }

    public void createVariableStack(){
        IMap<String, ISyntaxNode> map = new TreeMap<>(IComparator.STRING_DEFAULT_COMPARATOR);
        variableStack.push(map);
    }

}
