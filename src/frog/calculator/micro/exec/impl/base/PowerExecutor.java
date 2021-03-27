package frog.calculator.micro.exec.impl.base;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.math.number.NumberUtil;
import frog.calculator.micro.exception.IncorrectStructureException;
import frog.calculator.micro.exec.AbstractDyadicExecutor;
import frog.calculator.micro.exec.MicroExecuteContext;
import frog.calculator.util.StringUtils;

public class PowerExecutor extends AbstractDyadicExecutor {
    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber childA, ComplexNumber childB, MicroExecuteContext context) {
        if(childA == null || childB == null){
            throw new IncorrectStructureException(self.word(), "param incorrect, a : " + StringUtils.valueOf(childA) + ", b : " + StringUtils.valueOf(childB)); 
        }
        return NumberUtil.power(childA, childB);
    }
}
