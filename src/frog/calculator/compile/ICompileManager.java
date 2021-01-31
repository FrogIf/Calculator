package frog.calculator.compile;

import frog.calculator.compile.lexical.INamedTokenFactory;
import frog.calculator.compile.lexical.INumberTokenFactory;

public interface ICompileManager {

    INamedTokenFactory getNamedTokenFactory();

    INumberTokenFactory getNumberTokenFactory();
    
}
