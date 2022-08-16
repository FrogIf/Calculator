package sch.frog.calculator.compile.semantic.exec.exception;

import sch.frog.calculator.exception.CalculatorException;

public class ExecuteException extends CalculatorException{
    
    private static final long serialVersionUID = 1L;

    public ExecuteException(String msg){
        super(msg);
    }
    
}
