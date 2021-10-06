package sch.frog.calculator.math.number;

public abstract class AbstractBaseNumber implements IBaseNumber{

    protected AbstractBaseNumber(){
        // do nothing
    }

    @Override
    public String decimal(int scale, NumberRoundingMode roundingMode){
        return this.decimal(scale, roundingMode, false);
    }

    @Override
    public String scientificNotation(int scale, NumberRoundingMode roundingMode) {
        return this.scientificNotation(scale, roundingMode, false);
    }
}
