package sch.frog.calculator.compile.syntax.exception;

import sch.frog.calculator.compile.exception.CompileException;

/**
 * 语法树构建异常
 */
public class BuildException extends CompileException {
    
    private static final long serialVersionUID = 1L;

    public BuildException(String msg) {
        super(msg);
    }

}
