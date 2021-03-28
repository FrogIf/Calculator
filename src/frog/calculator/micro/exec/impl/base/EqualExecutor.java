package frog.calculator.micro.exec.impl.base;

import frog.calculator.common.exec.AbstractDyadicExecutor;
import frog.calculator.common.exec.exception.NonsupportOperateException;
import frog.calculator.common.exec.result.SymbolValue;
import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.compile.semantic.IValue;
import frog.calculator.compile.syntax.ISyntaxNode;

public class EqualExecutor extends AbstractDyadicExecutor {

    @Override
    protected IValue evaluate(ISyntaxNode self, IValue childA, IValue childB, IExecuteContext context) {
        if(childA instanceof SymbolValue){
            context.addVariable(((SymbolValue)childA).getSymbol(), childB);
        }else{
            throw new NonsupportOperateException(self.word(), "can't operate for " + childA.getClass());
        }
        return childB;
    }

    
}
