package sch.frog.calculator.compile.semantic.exec;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.semantic.result.GeneralResult;
import sch.frog.calculator.compile.semantic.result.IResult;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.util.collection.IList;
import sch.frog.calculator.util.collection.Iterator;
import sch.frog.calculator.util.collection.LinkedList;

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
