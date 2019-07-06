package frog.calculator;

import frog.calculator.register.IRegister;
import frog.calculator.resolve.IResolver;

public interface ICalculatorConfigure {

    IResolver getResolver();

    IRegister getRegister();

}
