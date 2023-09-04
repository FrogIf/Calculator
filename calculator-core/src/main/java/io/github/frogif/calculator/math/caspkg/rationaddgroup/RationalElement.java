package io.github.frogif.calculator.math.caspkg.rationaddgroup;

import io.github.frogif.calculator.number.impl.RationalNumber;
import io.github.frogif.calculator.math.cas.IElement;

public class RationalElement implements IElement {

    private final RationalNumber value;

    public RationalElement(RationalNumber value){
        this.value = value;
    }

    public RationalNumber getValue(){
        return this.value;
    }
    
}
