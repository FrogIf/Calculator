package io.github.frogif.calculator.cell.exec.impl.base;

import io.github.frogif.calculator.compile.semantic.IExecuteContext;
import io.github.frogif.calculator.compile.semantic.exec.AbstractDyadicExecutor;
import io.github.frogif.calculator.compile.semantic.exec.exception.NonsupportOperateException;
import io.github.frogif.calculator.compile.semantic.result.IValue;
import io.github.frogif.calculator.compile.semantic.result.VariableValue;
import io.github.frogif.calculator.compile.syntax.ISyntaxNode;

public class AssignExecutor extends AbstractDyadicExecutor {

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
