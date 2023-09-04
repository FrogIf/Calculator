package io.github.frogif.calculator.cell.exec.impl.base;

import io.github.frogif.calculator.compile.semantic.IExecuteContext;
import io.github.frogif.calculator.compile.semantic.exec.AbstractDyadicExecutor;
import io.github.frogif.calculator.compile.semantic.exec.exception.ExecuteException;
import io.github.frogif.calculator.compile.semantic.result.IValue;
import io.github.frogif.calculator.compile.semantic.result.NestValue;
import io.github.frogif.calculator.util.collection.ArrayList;
import io.github.frogif.calculator.util.collection.IList;
import io.github.frogif.calculator.util.collection.Iterator;
import io.github.frogif.calculator.compile.syntax.ISyntaxNode;

/**
 * 逗号分隔符运算器
 */
public class CommaExecutor extends AbstractDyadicExecutor {

    @Override
    protected IValue evaluate(ISyntaxNode self, IValue childA, IValue childB, IExecuteContext context) {
        ArrayList<IValue> vals = new ArrayList<>();
        if(childA instanceof NestValue){
            deconstruction((NestValue)childA, vals);
        }else{
            vals.add(childA);
        }

        if(childB instanceof NestValue){
            deconstruction((NestValue)childB, vals);
        }else{
            vals.add(childB);
        }
        return new NestValue(vals, NestValue.Direction.HORIZONTAL);
    }

    private void deconstruction(NestValue val, IList<IValue> vals){
        IList<IValue> nestValList = val.getValues();
        Iterator<IValue> itr = nestValList.iterator();
        while(itr.hasNext()){
            IValue nv = itr.next();
            if(nv instanceof NestValue){
                throw new ExecuteException("can't deconstruction this struct.");
            }else{
                vals.add(nv);
            }
        }
    }
    
}
