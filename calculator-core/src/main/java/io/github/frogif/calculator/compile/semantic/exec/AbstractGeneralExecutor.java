package io.github.frogif.calculator.compile.semantic.exec;

import io.github.frogif.calculator.compile.semantic.result.GeneralResult;
import io.github.frogif.calculator.compile.semantic.result.IResult;
import io.github.frogif.calculator.util.collection.IList;
import io.github.frogif.calculator.util.collection.Iterator;
import io.github.frogif.calculator.util.collection.LinkedList;
import io.github.frogif.calculator.compile.semantic.IExecuteContext;
import io.github.frogif.calculator.compile.syntax.ISyntaxNode;

public abstract class AbstractGeneralExecutor implements IExecutor {

    @Override
    public final IResult execute(ISyntaxNode token, IExecuteContext context) {
        IList<ISyntaxNode> children = token.children();
        IList<IResult> childResults = new LinkedList<>();
        if(children != null && children.size() > 0){
            Iterator<ISyntaxNode> iterator = children.iterator();
            while (iterator.hasNext()){
                ISyntaxNode child = iterator.next();
                if(child != null){
                    IResult result = child.execute(context);
                    childResults.add(result);
                }else{
                    childResults.add(new GeneralResult(IResult.ResultType.UNKNOWN));
                }
            }
        }

        return evaluate(token, childResults, context);
    }

    protected abstract GeneralResult evaluate(ISyntaxNode self, IList<IResult> childResults, IExecuteContext context);

}
