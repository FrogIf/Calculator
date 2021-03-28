package frog.calculator.common.exec;

import frog.calculator.common.exec.exception.IncorrectStructureException;
import frog.calculator.common.exec.exception.NonsupportOperateException;
import frog.calculator.common.exec.result.GeneralResult;
import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.compile.semantic.IResult;
import frog.calculator.compile.semantic.IValue;
import frog.calculator.compile.semantic.IResult.ResultType;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.util.collection.IList;

/**
 * 二值运算
 */
public abstract class AbstractDyadicExecutor extends AbstractGeneralExecutor {

    @Override
    protected GeneralResult evaluate(ISyntaxNode self, IList<IResult> children, IExecuteContext context) {
        if(children.size() != 2){
            throw new IncorrectStructureException(self.word(), "need 2 param, but is " + children.size());
        }
        IResult r0 = children.get(0);
        IResult r1 = children.get(1);
        if(r0.getResultType() == ResultType.VALUE && r1.getResultType() == ResultType.VALUE){
            IValue resultVal = evaluate(self, r0.getValue(), r1.getValue(), context);
            return new GeneralResult(resultVal);
        }else{
            throw new NonsupportOperateException(self.word(), "can't operate for this type : " + r0.getResultType().name() 
            + " and " + r1.getResultType().name());
        }
    }

    protected abstract IValue evaluate(ISyntaxNode self, IValue childA, IValue childB, IExecuteContext context);
}
