package io.github.frogif.calculator.compile.semantic.exec;

import io.github.frogif.calculator.compile.semantic.exec.exception.ExecuteException;
import io.github.frogif.calculator.compile.semantic.exec.exception.IncorrectStructureException;
import io.github.frogif.calculator.compile.semantic.result.GeneralResult;
import io.github.frogif.calculator.compile.semantic.result.IResult;
import io.github.frogif.calculator.compile.semantic.result.IValue;
import io.github.frogif.calculator.util.collection.IList;
import io.github.frogif.calculator.compile.semantic.IExecuteContext;
import io.github.frogif.calculator.compile.syntax.ISyntaxNode;

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
        IValue resultVal = evaluate(self, getValue(r0), getValue(r1), context);
        return new GeneralResult(resultVal);
    }

    protected abstract IValue evaluate(ISyntaxNode self, IValue childA, IValue childB, IExecuteContext context);

    private IValue getValue(IResult result){
        IResult.ResultType resultType = result.getResultType();
        if(resultType == IResult.ResultType.VALUE){
            return result.getValue();
        }else if(resultType == IResult.ResultType.UNKNOWN){
            return null;
        }else{
            throw new ExecuteException("can't get value from type : " + resultType.name());
        }
    }
}
