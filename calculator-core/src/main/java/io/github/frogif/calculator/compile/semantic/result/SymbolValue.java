package io.github.frogif.calculator.compile.semantic.result;

/**
 * 符号
 */
public class SymbolValue implements IValue{
    
    private final String symbol;

    public SymbolValue(String symbol){
        this.symbol = symbol;
    }

    public String getSymbol(){
        return this.symbol;
    }

}
