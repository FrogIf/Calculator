package io.github.frogif.calculator.compile.semantic;

import io.github.frogif.calculator.compile.semantic.result.IResult;
import io.github.frogif.calculator.exception.CalculatorException;

public class NoValueException extends CalculatorException {

    private static final long serialVersionUID = 1L;

    public NoValueException(IResult.ResultType resultType){
        super("no value fetch from this result. result type : " + resultType.name());
    }
    
}
