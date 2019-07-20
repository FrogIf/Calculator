package frog.calculator.connect;

import frog.calculator.register.IRegister;
import frog.calculator.resolver.IResolver;

public interface ICalculatorSession {
    IResolver getSessionResolver();

    IRegister getRegister();
}
