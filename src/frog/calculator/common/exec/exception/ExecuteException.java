package frog.calculator.common.exec.exception;

import frog.calculator.exception.CalculatorException;

public class ExecuteException extends CalculatorException{
    
    private static final long serialVersionUID = 1L;

    public ExecuteException(String msg){
        super(msg);
    }
    
}
