package frog.calculator.math;

public interface IComplexNumber extends INumber {

    RealNumber getRealPart();

    RealNumber getImaginaryPart();

    IComplexNumber add(IComplexNumber num);

    IComplexNumber sub(IComplexNumber num);

    IComplexNumber mult(IComplexNumber num);

    IComplexNumber div(IComplexNumber num);

}
