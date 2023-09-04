package io.github.frogif.calculator.cell.exec.impl.fun;

import io.github.frogif.calculator.compile.semantic.IExecuteContext;
import io.github.frogif.calculator.compile.syntax.ISyntaxNode;
import io.github.frogif.calculator.number.impl.ComplexNumber;
import io.github.frogif.calculator.number.impl.NumberSign;
import io.github.frogif.calculator.number.impl.RationalNumber;
import io.github.frogif.calculator.cell.CellSingleElementExecutor;

public class AbsExecutor extends CellSingleElementExecutor {
    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber child, IExecuteContext context) {
        RationalNumber imaginaryPart = child.getImaginaryPart();
        RationalNumber realPart = child.getRealPart();
        if(realPart != null){
            NumberSign sr = realPart.getSign();
            if(sr == NumberSign.NEGATIVE){
                realPart = realPart.not();
            }
        }
        if(imaginaryPart == null || imaginaryPart.equals(RationalNumber.ZERO)){
            return new ComplexNumber(realPart, imaginaryPart);
        }

        // TODO 求取复数的模
        return null;
    }
}
