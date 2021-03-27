package frog.calculator.micro.exec.impl.base;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.micro.exec.AbstractDyadicExecutor;
import frog.calculator.micro.exec.MicroExecuteContext;

public class SubExecutor extends AbstractDyadicExecutor {
    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber childA, ComplexNumber childB, MicroExecuteContext context) {
        if(childA == null){
            return childB.not();
        }else{
            return childA.sub(childB);
        }
    }
}
