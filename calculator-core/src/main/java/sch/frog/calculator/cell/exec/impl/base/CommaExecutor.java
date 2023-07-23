package sch.frog.calculator.cell.exec.impl.base;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.semantic.exec.AbstractDyadicExecutor;
import sch.frog.calculator.compile.semantic.exec.exception.ExecuteException;
import sch.frog.calculator.compile.semantic.result.IValue;
import sch.frog.calculator.compile.semantic.result.NestValue;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.util.collection.ArrayList;
import sch.frog.calculator.util.collection.IList;
import sch.frog.calculator.util.collection.Iterator;

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
