package frog.calculator.microexec;

import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.compile.semantic.IExecutor;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.LinkedList;

public abstract class AbstractMicroExecutor implements IExecutor {

    @Override
    public final void execute(ISyntaxNode token, IExecuteContext context) {
        MicroExecuteContext microExecuteContext = (MicroExecuteContext) context;
        IList<ISyntaxNode> children = token.children();
        this.checkChildrenBeforeExecute(token.word(), children);
        IList<ComplexNumber> childResults = new LinkedList<>();
        if(children != null && children.size() > 0){
            Iterator<ISyntaxNode> iterator = children.iterator();
            while (iterator.hasNext()){
                ISyntaxNode next = iterator.next();
                next.execute(microExecuteContext);

                IList<ComplexNumber> result = microExecuteContext.getResult();
                // 这里直接拆解结果
                if(result != null){
                    Iterator<ComplexNumber> resultItr = result.iterator();
                    while (resultItr.hasNext()){
                        childResults.add(resultItr.next());
                    }
                }
            }
        }

        IList<ComplexNumber> result = evaluate(token, childResults, microExecuteContext);
        microExecuteContext.setResult(result);
    }

    protected abstract void checkChildrenBeforeExecute(String word, IList<ISyntaxNode> children);

    protected abstract IList<ComplexNumber> evaluate(ISyntaxNode self, IList<ComplexNumber> children, MicroExecuteContext context);

}
