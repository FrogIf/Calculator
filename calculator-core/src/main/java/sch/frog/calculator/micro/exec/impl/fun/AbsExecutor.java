package sch.frog.calculator.micro.exec.impl.fun;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.math.number.ComplexNumber;
import sch.frog.calculator.math.number.NumberSign;
import sch.frog.calculator.math.number.RealNumber;
import sch.frog.calculator.micro.MicroSingleElementExecutor;

public class AbsExecutor extends MicroSingleElementExecutor {
    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber child, IExecuteContext context) {
        RealNumber imaginaryPart = child.getImaginaryPart();
        RealNumber realPart = child.getRealPart();
        if(realPart != null){
            NumberSign sr = realPart.getSign();
            if(sr == NumberSign.NEGATIVE){
                realPart = realPart.not();
            }
        }
        if(imaginaryPart == null || imaginaryPart.equals(RealNumber.ZERO)){
            return new ComplexNumber(realPart, imaginaryPart);
        }

        // TODO 求取复数的模
        return null;
    }
}
