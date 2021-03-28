package frog.calculator.micro;

import frog.calculator.common.exec.exception.ExecuteException;
import frog.calculator.common.exec.result.SymbolValue;
import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.compile.semantic.IValue;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.util.IComparator;
import frog.calculator.util.collection.ISet;
import frog.calculator.util.collection.RBTreeSet;

public class MicroUtil {

    private MicroUtil(){
        // do nothing.
    }

    public static ComplexNumber getNumber(IValue value, IExecuteContext context){
        if(value instanceof ComplexValue){
            return ((ComplexValue)value).getValue();
        }else if(value instanceof SymbolValue){
            String name = ((SymbolValue)value).getSymbol();
            ISet<String> treeSet = new RBTreeSet<>(IComparator.STRING_DEFAULT_COMPARATOR);
            treeSet.add(name);
            IValue val = context.getVariable(name);
            while(val instanceof SymbolValue){
                String nestName = ((SymbolValue)value).getSymbol();
                if(treeSet.contains(nestName)){
                    throw new IllegalStateException("recursion variable reference : " + nestName);
                }else{
                    treeSet.add(nestName);
                    val = context.getVariable(name);
                }
            }
            if(val instanceof ComplexValue){
                return ((ComplexValue)val).getValue(); 
            }else{
                throw new ExecuteException("can't get value from " + value.getClass());
            }
        }else{
            throw new ExecuteException("can't get value from " + value.getClass());
        }
    }

}
