package frog.calculator.math;

public interface IRealNumber extends IComplexNumber{

    RationalNumber getRationalPart();

    IrrationalNumber getIrrationalPart();

    RealNumber getNext();


}
