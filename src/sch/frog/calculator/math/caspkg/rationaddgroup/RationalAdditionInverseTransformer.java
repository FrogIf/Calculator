package sch.frog.calculator.math.caspkg.rationaddgroup;

import sch.frog.calculator.math.cas.IInverseElementTransformer;
import sch.frog.calculator.math.number.RationalNumber;

public class RationalAdditionInverseTransformer implements IInverseElementTransformer<RationalElement> {

    @Override
    public RationalElement transformToInverse(RationalElement source) {
        RationalNumber value = source.getValue();
        return new RationalElement(value.not());
    }
    
}
