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

public abstract class AbstractSingleElementExecutor extends AbstractGeneralExecutor {

    @Override
    protected GeneralResult evaluate(ISyntaxNode self, IList<IResult> children,
                                            IExecuteContext context) {
        if(children.size() != 1){
            throw new IncorrectStructureException(self.word(), "need 1 param, but is " + children.size());
        }

        IResult r0 = children.get(0);
        ResultType rt0 = r0.getResultType();
        if(r0.getResultType() == ResultType.VALUE){
            IValue resultVal = this.evaluate(self, r0.getValue(), context);
            return new GeneralResult(resultVal);
        }else{
            throw new NonsupportOperateException(self.word(), "can't operate for this type : " + rt0.name());
        }
    }

    protected abstract IValue evaluate(ISyntaxNode self, IValue child, IExecuteContext context);
}
