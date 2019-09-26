package frog.calculator.operator.util;

import frog.calculator.math.INumber;

public interface ISingleElementDealer {
    INumber deal(INumber left, INumber right);
}
