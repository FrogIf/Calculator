package frog.calculator.micro.exec.impl.base;

import frog.calculator.common.exec.AbstractDyadicExecutor;
import frog.calculator.common.exec.exception.NonsupportOperateException;
import frog.calculator.common.exec.result.VariableValue;
import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.compile.semantic.IValue;
import frog.calculator.compile.syntax.ISyntaxNode;

public class EqualExecutor extends AbstractDyadicExecutor {

    @Override
    protected IValue evaluate(ISyntaxNode self, IValue childA, IValue childB, IExecuteContext context) {
        if(childA instanceof VariableValue){
            VariableValue value = ((VariableValue)childA);
            value.setValue(getNestValue(childB));
            context.addVariable(value.getName(), value);
        }else{
            throw new NonsupportOperateException(self.word(), "can't operate for " + childA.getClass());
        }
        return childB;
    }

    private IValue getNestValue(IValue value){
        if(value instanceof VariableValue){
            return getNestValue(((VariableValue)value).getValue());
        }else{
            return value;
        }
    }

    
}
