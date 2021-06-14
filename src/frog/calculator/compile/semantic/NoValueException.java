package frog.calculator.compile.semantic;

import frog.calculator.compile.semantic.IResult.ResultType;
import frog.calculator.exception.CalculatorException;

public class NoValueException extends CalculatorException {

    private static final long serialVersionUID = 1L;

    public NoValueException(ResultType resultType){
        super("no value fetch from this result. result type : " + resultType.name());
    }
    
}
