package frog.calculator.math;

public abstract class AbstractPureIrrationalNumber extends AbstractIrrationalNumber {

    @Override
    public final RationalNumber tryGetRationalFactor() {
        return null;
    }

    @Override
    public final AbstractIrrationalNumber tryGetIrrationalFactor() {
        return this;
    }
}
