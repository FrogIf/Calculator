package frog.calculator.math;

public class FactorObject {

    private final RationalNumber rationalPart;

    private final AbstractIrrationalNumber irrationalPart;

    public FactorObject(RationalNumber rationalPart, AbstractIrrationalNumber irrationalPart) {
        this.rationalPart = rationalPart;
        this.irrationalPart = irrationalPart;
    }

    public RationalNumber getRationalPart() {
        return rationalPart;
    }

    public AbstractIrrationalNumber getIrrationalPart() {
        return irrationalPart;
    }
}
