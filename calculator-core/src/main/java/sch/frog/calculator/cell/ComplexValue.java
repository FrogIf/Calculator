package sch.frog.calculator.cell;

import sch.frog.calculator.compile.semantic.result.IValue;
import sch.frog.calculator.number.ComplexNumber;

public class ComplexValue implements IValue {

    private final ComplexNumber complexNumber;

    public ComplexValue(ComplexNumber number){
        this.complexNumber = number;
    }

    public ComplexNumber getValue(){
        return this.complexNumber;
    }
}
