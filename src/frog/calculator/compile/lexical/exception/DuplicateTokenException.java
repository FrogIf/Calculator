package frog.calculator.compile.lexical.exception;

import frog.calculator.compile.exception.CompileException;

public class DuplicateTokenException extends CompileException{

    private static final long serialVersionUID = 1L;

    public DuplicateTokenException(String msg) {
        super(msg);
    }
    
}
