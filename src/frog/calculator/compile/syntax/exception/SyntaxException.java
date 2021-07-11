package frog.calculator.compile.syntax.exception;

/**
 * 语法异常
 */
public class SyntaxException extends BuildException {
    
    public SyntaxException(String word, int position, int line) {
        super("error syntax near : " + word + " line : " + line + ", word at : " + position);
    }

    public SyntaxException(String word, int position) {
        super("error syntax near : " + word + " , word at : " + position);
    }

    private static final long serialVersionUID = 1L;
    
}
