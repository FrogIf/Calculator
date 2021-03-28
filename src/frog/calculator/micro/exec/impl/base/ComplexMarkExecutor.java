package frog.calculator.micro.exec.impl.base;

import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.micro.MicroSingleElementExecutor;

public class ComplexMarkExecutor extends MicroSingleElementExecutor {

    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber child, IExecuteContext context) {
        if(child == null){
            return ComplexNumber.I;
        }else{
            return ComplexNumber.I.mult(child);
        }
    }

}
