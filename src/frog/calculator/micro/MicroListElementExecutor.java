package frog.calculator.micro;

import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.compile.semantic.exec.AbstractSingleElementExecutor;
import frog.calculator.compile.semantic.exec.exception.NonsupportOperateException;
import frog.calculator.compile.semantic.result.IValue;
import frog.calculator.compile.semantic.result.NestValue;
import frog.calculator.compile.semantic.result.VariableValue;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;

public abstract class MicroListElementExecutor extends AbstractSingleElementExecutor{

    @Override
    protected IValue evaluate(ISyntaxNode self, IValue child, IExecuteContext context) {
        IList<ComplexNumber> args = new ArrayList<>();
        if(child != null){
            if(child instanceof ComplexValue || child instanceof VariableValue){
                args.add(MicroUtil.getNumber(child, context));
            }else if(child instanceof NestValue){
                NestValue vals = (NestValue) child;
                IList<IValue> values = vals.getValues();
                Iterator<IValue> itr = values.iterator();
                while(itr.hasNext()){
                    args.add(MicroUtil.getNumber(itr.next(), context));
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
