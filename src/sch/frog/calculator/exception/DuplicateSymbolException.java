package sch.frog.calculator.exception;

public class DuplicateSymbolException extends CompileException {
    
    private static final long serialVersionUID = 1L;

    public DuplicateSymbolException(String msg) {
        super(msg);
    }
}
