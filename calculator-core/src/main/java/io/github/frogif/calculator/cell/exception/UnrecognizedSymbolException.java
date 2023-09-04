package io.github.frogif.calculator.cell.exception;

public class UnrecognizedSymbolException extends RuntimeException{

    public UnrecognizedSymbolException(String symbol) {
        super("unrecognized symbol : " + symbol + " for execute");
    }
}
