package frog.calculator.micro.exec.impl.base;

import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.compile.semantic.IExecutor;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.micro.exception.IncorrectStructureException;
import frog.calculator.micro.exec.MicroExecuteContext;
import frog.calculator.util.collection.IList;

public class DeclareExecutor implements IExecutor{

    @Override
    public void execute(ISyntaxNode token, IExecuteContext context) {
        IList<ISyntaxNode> children = token.children();
        if(children.size() != 1){
            throw new IncorrectStructureException(token.word(), "declare expect one child, but here is :" + children.size());
        }
        ISyntaxNode node = children.get(0);
        MicroExecuteContext microExecuteContext = (MicroExecuteContext) context;
        microExecuteContext.addVariable(node);
    }
    
}
