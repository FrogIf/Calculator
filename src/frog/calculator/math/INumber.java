package frog.calculator.math;

public interface INumber extends Comparable<INumber>{

    INumber add(INumber number);

    INumber sub(INumber number);

    INumber mult(INumber number);

    INumber div(INumber number);

}
