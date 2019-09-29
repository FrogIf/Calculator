package frog.calculator.math;

public abstract class AbstractRealNumber extends AbstractComplexNumber implements IRealNumber {

    @Override
    public final AbstractRealNumber getRealPart() {
        return this;
    }

    @Override
    public final AbstractRealNumber getImaginaryPart() {
        return null;
    }

    public abstract AbstractRealNumber not();

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AbstractRealNumber){
        }
        return false;
    }
}
