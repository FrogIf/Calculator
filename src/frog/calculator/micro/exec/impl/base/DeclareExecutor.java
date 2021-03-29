package frog.calculator.micro.exec.impl.base;

import frog.calculator.common.exec.exception.NonsupportOperateException;
import frog.calculator.common.exec.result.GeneralResult;
import frog.calculator.common.exec.result.VariableValue;
import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.compile.semantic.IExecutor;
import frog.calculator.compile.semantic.IResult;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.util.collection.IList;

public class DeclareExecutor implements IExecutor{

    @Override
    public IResult execute(ISyntaxNode self, IExecuteContext context) {
        IList<ISyntaxNode> children = self.children();
        if(children == null || children.size() != 1){
            throw new NonsupportOperateException(self.word(), "can't execute, expect 1 child, but " + (children == null ? "null" : children.size()));
        }
        String word = children.get(0).word();
        context.addVariable(children.get(0).word(), null);
        return new GeneralResult(new VariableValue(word));
    }
    
}
