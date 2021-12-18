package sch.frog.calculator.math.caspkg.rationaddgroup;

import sch.frog.calculator.math.cas.IElement;
import sch.frog.calculator.math.number.RationalNumber;

public class RationalElement implements IElement {

    private final RationalNumber value;

    public RationalElement(RationalNumber value){
        this.value = value;
    }

    public RationalNumber getValue(){
        return this.value;
    }
    
}
