package frog.calculator.operate.doubleopr;

import frog.calculator.operate.IOperator;

public interface IDiscriminator {

    IOperator recognize(String symbol);
}
