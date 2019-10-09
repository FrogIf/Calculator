package frog.calculator.operator.util;

import frog.calculator.math.BaseNumber;

public interface ILeftRightMapDealer {
    BaseNumber deal(BaseNumber left, BaseNumber right);
}
