package frog.calculator.math;

public abstract class AbstractNumber implements INumber {

    @Override
    public INumber add(INumber num) {
        if(num instanceof AbstractComplexNumber && this instanceof AbstractComplexNumber){
            return ((AbstractComplexNumber)this).add((AbstractComplexNumber)num);
        }
        return null;
    }

    @Override
    public INumber sub(INumber num) {
        if(num instanceof AbstractComplexNumber && this instanceof AbstractComplexNumber){
            return ((AbstractComplexNumber)this).sub((AbstractComplexNumber)num);
        }
        return null;
    }

    @Override
    public INumber mult(INumber num) {
        if(num instanceof AbstractComplexNumber && this instanceof AbstractComplexNumber){
            return ((AbstractComplexNumber)this).mult((AbstractComplexNumber)num);
        }
        return null;
    }

    @Override
    public INumber div(INumber num) {
        if(num instanceof AbstractComplexNumber && this instanceof AbstractComplexNumber){
            return ((AbstractComplexNumber)this).div((AbstractComplexNumber)num);
        }
        return null;
    }
}
