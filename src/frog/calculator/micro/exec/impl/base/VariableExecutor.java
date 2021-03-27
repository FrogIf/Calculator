package frog.calculator.micro.exec.impl.base;

import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.compile.semantic.IExecutor;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.micro.exception.IncorrectStructureException;
import frog.calculator.micro.exec.MicroExecuteContext;
import frog.calculator.util.collection.IList;

public class VariableExecutor implements IExecutor {

    @Override
    public void execute(ISyntaxNode token, IExecuteContext context) {
        IList<ISyntaxNode> children = token.children();
        if(children.size() != 0){
            throw new IncorrectStructureException(token.word(), "declare expect 0 child, but here is :" + children.size());
        }
        MicroExecuteContext microExecuteContext = (MicroExecuteContext) context;
        ISyntaxNode val = microExecuteContext.getVariable(token.word());
        val.execute(context);
    }
    
}
