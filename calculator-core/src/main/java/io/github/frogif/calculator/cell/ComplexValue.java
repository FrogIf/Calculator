package io.github.frogif.calculator.cell;

import io.github.frogif.calculator.compile.semantic.result.IValue;
import io.github.frogif.calculator.number.impl.ComplexNumber;

public class ComplexValue implements IValue {

    private final ComplexNumber complexNumber;

    public ComplexValue(ComplexNumber number){
        this.complexNumber = number;
    }

    public ComplexNumber getValue(){
        return this.complexNumber;
    }
}
