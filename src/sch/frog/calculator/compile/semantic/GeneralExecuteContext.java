package sch.frog.calculator.compile.semantic;

import sch.frog.calculator.compile.semantic.result.IValue;
import sch.frog.calculator.runtime.ICalculatorSession;
import sch.frog.calculator.util.IComparator;
import sch.frog.calculator.util.collection.IMap;
import sch.frog.calculator.util.collection.Iterator;
import sch.frog.calculator.util.collection.Stack;
import sch.frog.calculator.util.collection.TreeMap;

public class GeneralExecuteContext implements IExecuteContext {

    private final ICalculatorSession session;

    private final Stack<IMap<String, IValue>> variableStack = new Stack<>();

    public GeneralExecuteContext(ICalculatorSession session){
        this.session = session;
    }

    @Override
    public void addVariable(String name, IValue value){
        if(variableStack.isEmpty()){
            session.addVariable(name, value);
        }else{
            variableStack.top().put(name, value);
        }
    }

    @Override
    public IValue getVariable(String name){
        IValue val = null;

        Iterator<IMap<String, IValue>> itr = variableStack.invertedIterator();
        while(val == null && itr.hasNext()){
            IMap<String, IValue> map = itr.next();
            val = map.get(name);
        }

        if(val == null){
            val = session.getVariable(name);
        }
        return val;
    }

    @Override
    public void createVariableStack(){
        IMap<String, IValue> map = new TreeMap<>(IComparator.STRING_DEFAULT_COMPARATOR);
        variableStack.push(map);
    }

    @Override
    public void destoryTopStack(){
        variableStack.pop();
    }

}
