package frog.calculator.microexec.impl.base;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.microexec.AbstractSingleElementExecutor;
import frog.calculator.microexec.MicroExecuteContext;

public class ComplexMarkExecutor extends AbstractSingleElementExecutor {

    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber child, MicroExecuteContext context) {
        if(child == null){
            return ComplexNumber.I;
        }else{
            return ComplexNumber.I.mult(child);
        }
    }

}
