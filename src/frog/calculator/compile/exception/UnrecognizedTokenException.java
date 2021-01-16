package frog.calculator.compile.exception;

public class UnrecognizedTokenException extends CompileException{

    private static final long serialVersionUID = 1L;

    public UnrecognizedTokenException(char ch, int position) {
        super("unrecognized char : " + ch + " on position: " + position);
    }

}