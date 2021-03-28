package frog.calculator.compile.semantic;

public abstract class AbstractResult implements IResult {

    protected ResultType resultType = ResultType.VOID;

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
