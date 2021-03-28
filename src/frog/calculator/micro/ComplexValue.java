package frog.calculator.micro;

import frog.calculator.compile.semantic.IValue;
import frog.calculator.math.number.ComplexNumber;

public class ComplexValue implements IValue{

    private final ComplexNumber complexNumber;

    public ComplexValue(ComplexNumber number){
        this.complexNumber = number;
    }

    public ComplexNumber getValue(){
        return this.complexNumber;
    }
}
