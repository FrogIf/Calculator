package io.github.frogif.calculator.cell.exec.impl.fun;

import io.github.frogif.calculator.util.collection.IList;
import io.github.frogif.calculator.util.collection.Iterator;
import io.github.frogif.calculator.compile.semantic.IExecuteContext;
import io.github.frogif.calculator.compile.semantic.exec.exception.IncorrectStructureException;
import io.github.frogif.calculator.compile.syntax.ISyntaxNode;
import io.github.frogif.calculator.number.impl.ComplexNumber;
import io.github.frogif.calculator.cell.CellListElementExecutor;

public class SumExecutor extends CellListElementExecutor {

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
