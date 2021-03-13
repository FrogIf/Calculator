package frog.calculator.microexec.impl.base;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.microexec.AbstractMicroExecutor;
import frog.calculator.microexec.MicroExecuteContext;
import frog.calculator.microexec.exception.IncorrectStructureException;
import frog.calculator.util.collection.IList;

/**
 * 逗号分隔符运算器
 */
public class DotExecutor extends AbstractMicroExecutor{

    @Override
    protected IList<ComplexNumber> evaluate(ISyntaxNode self, IList<ComplexNumber> children,
            MicroExecuteContext context) {
        if(children.size() < 2){
            throw new IncorrectStructureException(self.word(), "need param count greater than 2, but is " + children.size());
        }
        return children;
    }
    
}
