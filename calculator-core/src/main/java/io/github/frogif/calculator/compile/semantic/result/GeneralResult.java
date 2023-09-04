package io.github.frogif.calculator.compile.semantic.result;

public class GeneralResult extends AbstractResult {

    public GeneralResult(){
        // do nothing
    }

    public GeneralResult(ResultType resultType){
        this.resultType = resultType;
    }

    public GeneralResult(IValue value){
        this.setValue(value);
    }

    public void setValue(IValue value){
        this.value = value;
        this.resultType = ResultType.VALUE;
    }
    
}
