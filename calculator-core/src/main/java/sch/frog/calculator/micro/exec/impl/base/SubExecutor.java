package sch.frog.calculator.micro.exec.impl.base;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.math.number.ComplexNumber;
import sch.frog.calculator.micro.MicroDyadicExecutor;

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
