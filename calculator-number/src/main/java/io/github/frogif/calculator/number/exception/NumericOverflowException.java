package io.github.frogif.calculator.number.exception;

public class NumericOverflowException extends ArithmeticException{
    

    public NumericOverflowException(){
        super("out of system express range.");
    }

    public NumericOverflowException(String msg){
        super(msg);
    }

}
