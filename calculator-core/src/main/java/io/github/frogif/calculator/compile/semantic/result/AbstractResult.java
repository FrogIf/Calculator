package io.github.frogif.calculator.compile.semantic.result;

import io.github.frogif.calculator.compile.semantic.NoValueException;

public abstract class AbstractResult implements IResult {

    protected ResultType resultType = ResultType.UNKNOWN;

    protected IValue value;

    @Override
    public ResultType getResultType() {
        return this.resultType;
    }

    @Override
    public IValue getValue() throws NoValueException {
        if(this.resultType == ResultType.VALUE){
            return this.value;
        }else{
            throw new NoValueException(resultType);
        }
    }
    
}
