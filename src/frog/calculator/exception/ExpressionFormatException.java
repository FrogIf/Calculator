package frog.calculator.exception;

public class ExpressionFormatException extends CompileException {

    private static final long serialVersionUID = 1L;

    public ExpressionFormatException(String expression, String msg) {
        super("expression '" + expression + "'; message '" + msg + "'");
    }

}
