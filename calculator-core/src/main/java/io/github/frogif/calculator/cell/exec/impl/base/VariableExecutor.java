package io.github.frogif.calculator.cell.exec.impl.base;

import io.github.frogif.calculator.compile.semantic.IExecuteContext;
import io.github.frogif.calculator.compile.semantic.exec.IExecutor;
import io.github.frogif.calculator.compile.semantic.exec.exception.IncorrectStructureException;
import io.github.frogif.calculator.compile.semantic.result.GeneralResult;
import io.github.frogif.calculator.compile.semantic.result.IResult;
import io.github.frogif.calculator.compile.semantic.result.IValue;
import io.github.frogif.calculator.util.collection.IList;
import io.github.frogif.calculator.compile.syntax.ISyntaxNode;

public class VariableExecutor implements IExecutor {

    @Override
    public IResult execute(ISyntaxNode self, IExecuteContext context) {
        IList<ISyntaxNode> children = self.children();
        if(children != null && children.size() != 0){
            throw new IncorrectStructureException(self.word(), "declare expect 0 child, but here is :" + children.size());
        }
        IValue value = context.getVariable(self.word());
        return new GeneralResult(value);
    }
    
}
