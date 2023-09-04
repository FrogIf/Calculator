package io.github.frogif.calculator.exception;

public class CalculatorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CalculatorException(){
        // do nothing
    }

    public CalculatorException(String msg){
        super(msg);
    }
    
}
