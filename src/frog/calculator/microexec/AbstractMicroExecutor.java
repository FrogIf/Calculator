package frog.calculator.microexec;

import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.compile.semantic.IExecutor;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.microexec.exception.ExecuteException;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.LinkedList;

public abstract class AbstractMicroExecutor implements IExecutor {

    private static final ArrayList<ComplexNumber> NULL_RESULT = new ArrayList<>(new ComplexNumber[]{null});

    @Override
    public final void execute(ISyntaxNode token, IExecuteContext context) {
        MicroExecuteContext microExecuteContext = (MicroExecuteContext) context;
        IList<ISyntaxNode> children = token.children();
        IList<ComplexNumber> childResults = new LinkedList<>();
        if(children != null && children.size() > 0){
            Iterator<ISyntaxNode> iterator = children.iterator();
            while (iterator.hasNext()){
                ISyntaxNode next = iterator.next();
                IList<ComplexNumber> result;
                if(next == null){
                    result = NULL_RESULT;
                }else{
                    next.execute(microExecuteContext);
                    result = microExecuteContext.getResult();
                }

                if(result != null){
                    Iterator<ComplexNumber> resultItr = result.iterator();
                    while (resultItr.hasNext()){
                        childResults.add(resultItr.next());
                    }
                }else{
                    throw new ExecuteException("the execute return null. token : [" + token.word() + "]");
                }
            }
        }

        IList<ComplexNumber> result = evaluate(token, childResults, microExecuteContext);
        microExecuteContext.setResult(result);
    }

    protected abstract IList<ComplexNumber> evaluate(ISyntaxNode self, IList<ComplexNumber> children, MicroExecuteContext context);

}
