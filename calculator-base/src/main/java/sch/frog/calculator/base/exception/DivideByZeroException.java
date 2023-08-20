package sch.frog.calculator.base.exception;

public class DivideByZeroException extends ArithmeticException {

    public DivideByZeroException() {
        super("/ by zero");
    }

}
