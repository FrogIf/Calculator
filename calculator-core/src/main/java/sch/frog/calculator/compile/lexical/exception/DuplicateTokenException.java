package sch.frog.calculator.compile.lexical.exception;

import sch.frog.calculator.exception.CompileException;

/**
 * 重复token异常
 */
public class DuplicateTokenException extends CompileException {

    private static final long serialVersionUID = 1L;

    public DuplicateTokenException(String word) {
        super("token {" + word + "} is duplicate define.");
    }
    
}
