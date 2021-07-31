package sch.frog.calculator.compile.exception;

import sch.frog.calculator.exception.CalculatorException;

/**
 * 编译异常
 */
public class CompileException extends CalculatorException{

    private static final long serialVersionUID = 1L;

    public CompileException(String msg) {
        super(msg);
    }
    
}
