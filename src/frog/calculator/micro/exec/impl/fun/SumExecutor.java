package frog.calculator.micro.exec.impl.fun;

import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.compile.semantic.exec.exception.IncorrectStructureException;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.micro.MicroListElementExecutor;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;

public class SumExecutor extends MicroListElementExecutor {

    @Override
    protected ComplexNumber funEvaluate(ISyntaxNode self, IList<ComplexNumber> args, IExecuteContext context) {
        if(args.isEmpty()){
            throw new IncorrectStructureException(self.word(), "there is no children");
        }
        Iterator<ComplexNumber> iterator = args.iterator();
        ComplexNumber sum = ComplexNumber.ZERO;
        while (iterator.hasNext()){
            sum = sum.add(iterator.next());
        }
        return sum;
    }
}
