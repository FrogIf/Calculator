package frog.calculator.compile.exception;

import frog.calculator.exception.CalculatorExpression;

/**
 * 编译异常
 */
public class CompileException extends CalculatorExpression{

    private static final long serialVersionUID = 1L;

    public CompileException(String msg) {
        super(msg);
    }
    
}
