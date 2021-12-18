package sch.frog.calculator.math.caspkg.rationaddgroup;

import sch.frog.calculator.math.cas.IOperator;

public class RationalAdditionOperator implements IOperator<RationalElement> {

    @Override
    public RationalElement operate(RationalElement left, RationalElement right) {
        return new RationalElement(left.getValue().add(right.getValue()));
    }
    
}
