package frog.calculator.exception;

public class ExpressionFormatException extends BuildException {

    public ExpressionFormatException(String expression, String msg) {
        super("expression : " + expression + "; message : " + msg);
    }

}
