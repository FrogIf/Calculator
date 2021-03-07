package frog.calculator.microexec.impl.fun;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.microexec.AbstractMicroExecutor;
import frog.calculator.microexec.MicroExecuteContext;
import frog.calculator.microexec.exception.IncorrectStructureException;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;

public class SumExecutor extends AbstractMicroExecutor {
    @Override
    protected void checkChildrenBeforeExecute(String word, IList<ISyntaxNode> children) {
        if(children.isEmpty()){
            throw new IncorrectStructureException(word, "there is no children");
        }
    }

    @Override
    protected IList<ComplexNumber> evaluate(ISyntaxNode self, IList<ComplexNumber> children, MicroExecuteContext context) {
        Iterator<ComplexNumber> iterator = children.iterator();
        ComplexNumber sum = ComplexNumber.ZERO;
        while (iterator.hasNext()){
            sum = sum.add(iterator.next());
        }
        ArrayList<ComplexNumber> result = new ArrayList<>(1);
        result.add(sum);
        return result;
    }
}
