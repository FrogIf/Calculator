package sch.frog.calculator.compile.semantic;

import sch.frog.calculator.compile.semantic.result.IResult;
import sch.frog.calculator.exception.CalculatorException;

public class NoValueException extends CalculatorException {

    private static final long serialVersionUID = 1L;

    public NoValueException(IResult.ResultType resultType){
        super("no value fetch from this result. result type : " + resultType.name());
    }
    
}
