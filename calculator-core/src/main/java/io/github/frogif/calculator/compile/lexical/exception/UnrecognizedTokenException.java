package io.github.frogif.calculator.compile.lexical.exception;

import io.github.frogif.calculator.exception.CompileException;

/**
 * 未识别的token异常
 */
public class UnrecognizedTokenException extends CompileException {

    private static final long serialVersionUID = 1L;

    public UnrecognizedTokenException(char ch, int position) {
        super("unrecognized char : '" + ch + "' on position: " + position);
    }

}