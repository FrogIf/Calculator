package frog.calculator.micro.exec.impl.base;

import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.compile.semantic.exec.exception.IncorrectStructureException;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.math.number.NumberUtil;
import frog.calculator.micro.MicroDyadicExecutor;
import frog.calculator.util.StringUtils;

public class PowerExecutor extends MicroDyadicExecutor {

    @Override
    protected ComplexNumber evaluate(ISyntaxNode self, ComplexNumber childA, ComplexNumber childB,
            IExecuteContext context) {
        if(childA == null || childB == null){
            throw new IncorrectStructureException(self.word(), "param incorrect, a : " + StringUtils.valueOf(childA) + ", b : " + StringUtils.valueOf(childB)); 
        }
        return NumberUtil.power(childA, childB);
    }
}
