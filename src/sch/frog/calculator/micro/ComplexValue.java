package sch.frog.calculator.micro;

import sch.frog.calculator.compile.semantic.result.IValue;
import sch.frog.calculator.math.number.ComplexNumber;

public class ComplexValue implements IValue{

    private final ComplexNumber complexNumber;

    public ComplexValue(ComplexNumber number){
        this.complexNumber = number;
    }

    public ComplexNumber getValue(){
        return this.complexNumber;
    }
}
