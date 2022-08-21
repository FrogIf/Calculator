package sch.frog.calculator.micro.exception;

public class UnrecognizedSymbolException extends RuntimeException{

    public UnrecognizedSymbolException(String symbol) {
        super("unrecognized symbol : " + symbol + " for execute");
    }
}
