package frog.calculator.compile.exception;

public class SyntaxException extends BuildException {
    
    public SyntaxException(String word, int position, int line) {
        super("error syntax near : " + word + " line : " + line + ", word at : " + position);
    }

    private static final long serialVersionUID = 1L;
    
}
