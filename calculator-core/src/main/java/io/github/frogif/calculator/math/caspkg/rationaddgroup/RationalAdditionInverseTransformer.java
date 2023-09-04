package io.github.frogif.calculator.math.caspkg.rationaddgroup;

import io.github.frogif.calculator.number.impl.RationalNumber;
import io.github.frogif.calculator.math.cas.IInverseElementTransformer;

public class RationalAdditionInverseTransformer implements IInverseElementTransformer<RationalElement> {

    @Override
    public RationalElement transformToInverse(RationalElement source) {
        RationalNumber value = source.getValue();
        return new RationalElement(value.not());
    }
    
}
