package frog.calculator.micro.exception;

import frog.calculator.exception.CalculatorExpression;

public class ExecuteException extends CalculatorExpression{
    
    private static final long serialVersionUID = 1L;

    public ExecuteException(String msg){
        super(msg);
    }
    
}
