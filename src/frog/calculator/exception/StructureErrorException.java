package frog.calculator.exception;

public class StructureErrorException extends RuntimeException {

    public StructureErrorException(String symbol, int order){
        super("structure is not right. symbol : " + symbol + ", order : " + order);
    }

    public StructureErrorException(){
        super("structure is not right.");
    }

}
