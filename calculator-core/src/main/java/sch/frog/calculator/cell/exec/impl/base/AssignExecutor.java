package sch.frog.calculator.cell.exec.impl.base;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.semantic.exec.AbstractDyadicExecutor;
import sch.frog.calculator.compile.semantic.exec.exception.NonsupportOperateException;
import sch.frog.calculator.compile.semantic.result.IValue;
import sch.frog.calculator.compile.semantic.result.VariableValue;
import sch.frog.calculator.compile.syntax.ISyntaxNode;

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
