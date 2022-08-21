package sch.frog.calculator.micro.exec.impl.base;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.math.number.ComplexNumber;
import sch.frog.calculator.micro.MicroDyadicExecutor;
import sch.frog.calculator.micro.exception.UnrecognizedSymbolException;

public class PlusMinusExecutor extends MicroDyadicExecutor {
    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber childA, ComplexNumber childB, IExecuteContext context) {
        String word = self.word();
        if("+".equals(word)){
            return add(childA, childB);
        }else if("-".equals(word)){
            return sub(childA, childB);
        }else{
            throw new UnrecognizedSymbolException(word);
        }
    }

    private ComplexNumber add(ComplexNumber childA, ComplexNumber childB){
        if(childA == null){
            return childB;
        }else{
            return childA.add(childB);
        }
    }

    private ComplexNumber sub(ComplexNumber childA, ComplexNumber childB){
        if(childA == null){
            return childB.not();
        }else{
            return childA.sub(childB);
        }
    }
}
