package io.github.frogif.calculator.compile.semantic.exec;

import io.github.frogif.calculator.compile.semantic.exec.exception.IncorrectStructureException;
import io.github.frogif.calculator.compile.semantic.exec.exception.NonsupportOperateException;
import io.github.frogif.calculator.compile.semantic.result.GeneralResult;
import io.github.frogif.calculator.compile.semantic.result.IResult;
import io.github.frogif.calculator.compile.semantic.result.IValue;
import io.github.frogif.calculator.util.collection.IList;
import io.github.frogif.calculator.compile.semantic.IExecuteContext;
import io.github.frogif.calculator.compile.syntax.ISyntaxNode;

public abstract class AbstractSingleElementExecutor extends AbstractGeneralExecutor {

    @Override
    protected GeneralResult evaluate(ISyntaxNode self, IList<IResult> children,
                                     IExecuteContext context) {
        if(children.size() != 1){
            throw new IncorrectStructureException(self.word(), "need 1 param, but is " + children.size());
        }

        IResult r0 = children.get(0);
        IResult.ResultType rt0 = r0.getResultType();
        if(r0.getResultType() == IResult.ResultType.VALUE){
            IValue resultVal = this.evaluate(self, r0.getValue(), context);
            return new GeneralResult(resultVal);
        }else{
            throw new NonsupportOperateException(self.word(), "can't operate for this type : " + rt0.name());
        }
    }

    protected abstract IValue evaluate(ISyntaxNode self, IValue child, IExecuteContext context);
}
