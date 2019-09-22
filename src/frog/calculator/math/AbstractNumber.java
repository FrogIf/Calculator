package frog.calculator.math;

public abstract class AbstractNumber implements INumber {

    @Override
    public INumber add(INumber num) {
        if(num instanceof IComplexNumber && this instanceof IComplexNumber){
            return ((IComplexNumber)this).add((IComplexNumber)num);
        }
        return null;
    }

    @Override
    public INumber sub(INumber num) {
        if(num instanceof ComplexNumber && this instanceof ComplexNumber){
            return ((IComplexNumber)this).sub((IComplexNumber)num);
        }
        return null;
    }

    @Override
    public INumber mult(INumber num) {
        if(num instanceof ComplexNumber && this instanceof ComplexNumber){
            return ((IComplexNumber)this).mult((IComplexNumber)num);
        }
        return null;
    }

    @Override
    public INumber div(INumber num) {
        if(num instanceof ComplexNumber && this instanceof ComplexNumber){
            return ((IComplexNumber)this).div((IComplexNumber)num);
        }
        return null;
    }
}
