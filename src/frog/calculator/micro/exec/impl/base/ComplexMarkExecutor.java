package frog.calculator.micro.exec.impl.base;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.micro.exec.AbstractSingleElementExecutor;
import frog.calculator.micro.exec.MicroExecuteContext;

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
