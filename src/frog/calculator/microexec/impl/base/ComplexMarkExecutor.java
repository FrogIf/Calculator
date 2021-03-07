package frog.calculator.microexec.impl.base;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.microexec.AbstractSingleElementExecutor;
import frog.calculator.microexec.MicroExecuteContext;
import frog.calculator.microexec.exception.IncorrectStructureException;
import frog.calculator.util.collection.IList;

public class ComplexMarkExecutor extends AbstractSingleElementExecutor {

    @Override
    protected void checkChildrenBeforeExecute(String word, IList<ISyntaxNode> children) {
        if(children.size() > 1){
            throw new IncorrectStructureException(word, "need 1 param, but is " + children.size());
        }
    }

    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber child, MicroExecuteContext context) {
        if(child == null){
            return ComplexNumber.I;
        }else{
            return ComplexNumber.I.mult(child);
        }
    }

}
