package frog.calculator.common.exec.result;

import frog.calculator.compile.semantic.IValue;

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
