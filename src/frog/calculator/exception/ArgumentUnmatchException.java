package frog.calculator.exception;

public class ArgumentUnmatchException extends RuntimeException {

    public ArgumentUnmatchException(String funName) {
        super(funName + " : argument is un match.");
    }
}
