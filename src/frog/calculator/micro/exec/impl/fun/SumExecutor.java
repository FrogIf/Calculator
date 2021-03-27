package frog.calculator.micro.exec.impl.fun;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.micro.exception.IncorrectStructureException;
import frog.calculator.micro.exec.AbstractMicroExecutor;
import frog.calculator.micro.exec.MicroExecuteContext;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;

public class SumExecutor extends AbstractMicroExecutor {

    @Override
    protected IList<ComplexNumber> evaluate(ISyntaxNode self, IList<ComplexNumber> children, MicroExecuteContext context) {
        if(children.isEmpty()){
            throw new IncorrectStructureException(self.word(), "there is no children");
        }
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
