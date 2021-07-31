package sch.frog.calculator.micro.exec.impl.base;

import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.semantic.exec.exception.IncorrectStructureException;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.math.number.ComplexNumber;
import sch.frog.calculator.math.number.NumberUtil;
import sch.frog.calculator.micro.MicroDyadicExecutor;
import sch.frog.calculator.util.StringUtils;

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
