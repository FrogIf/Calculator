package frog.calculator.math.real;

import frog.calculator.math.INumber;
import frog.calculator.math.rational.RationalNumber;

public abstract class AbstractStructureNumber implements INumber {

    protected abstract RationalNumber tryConvertToRational();

}
