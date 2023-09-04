package io.github.frogif.calculator.cell;

import io.github.frogif.calculator.util.collection.ArrayList;
import io.github.frogif.calculator.util.collection.IList;
import io.github.frogif.calculator.util.collection.Iterator;
import io.github.frogif.calculator.compile.semantic.IExecuteContext;
import io.github.frogif.calculator.compile.semantic.exec.AbstractSingleElementExecutor;
import io.github.frogif.calculator.compile.semantic.exec.exception.NonsupportOperateException;
import io.github.frogif.calculator.compile.semantic.result.IValue;
import io.github.frogif.calculator.compile.semantic.result.NestValue;
import io.github.frogif.calculator.compile.semantic.result.VariableValue;
import io.github.frogif.calculator.compile.syntax.ISyntaxNode;
import io.github.frogif.calculator.number.impl.ComplexNumber;

public abstract class CellListElementExecutor extends AbstractSingleElementExecutor {

    @Override
    protected IValue evaluate(ISyntaxNode self, IValue child, IExecuteContext context) {
        IList<ComplexNumber> args = new ArrayList<>();
        if(child != null){
            if(child instanceof ComplexValue || child instanceof VariableValue){
                args.add(CellUtil.getNumber(child, context));
            }else if(child instanceof NestValue){
                NestValue vals = (NestValue) child;
                IList<IValue> values = vals.getValues();
                Iterator<IValue> itr = values.iterator();
                while(itr.hasNext()){
                    args.add(CellUtil.getNumber(itr.next(), context));
                }
            }else{
                throw new NonsupportOperateException(self.word(), "can't get value from " + child.getClass());
            }
        }
        ComplexNumber result = this.funEvaluate(self, args, context);
        return new ComplexValue(result);
    }

    protected abstract ComplexNumber funEvaluate(ISyntaxNode self, IList<ComplexNumber> args, IExecuteContext context);
    
    
}
