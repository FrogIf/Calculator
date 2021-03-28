package frog.calculator.micro.exec.impl.base;

import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.micro.MicroDyadicExecutor;
public class SubExecutor extends MicroDyadicExecutor {

    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber childA, ComplexNumber childB,
            IExecuteContext context) {
        if(childA == null){
            return childB.not();
        }else{
            return childA.sub(childB);
        }
    }
}
