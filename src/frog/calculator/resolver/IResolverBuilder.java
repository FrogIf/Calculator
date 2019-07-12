package frog.calculator.resolver;

import frog.calculator.register.IRegister;

public interface IResolverBuilder {

    IResolver getResolver();

    IRegister getRegister();

}
