package io.github.frogif.calculator.compile.semantic.exec.exception;

import io.github.frogif.calculator.exception.CalculatorException;

public class ExecuteException extends CalculatorException {
    
    private static final long serialVersionUID = 1L;

    public ExecuteException(String msg){
        super(msg);
    }
    
}
