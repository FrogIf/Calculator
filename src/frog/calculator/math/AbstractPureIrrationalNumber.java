package frog.calculator.math;

public abstract class AbstractPureIrrationalNumber extends AbstractIrrationalNumber {

    private FactorObject factorObject = null;

    @Override
    protected final FactorObject factorization() {
        if(this.factorObject == null){
            this.factorObject = new FactorObject(null, this);
        }
        return this.factorObject;
    }
}
