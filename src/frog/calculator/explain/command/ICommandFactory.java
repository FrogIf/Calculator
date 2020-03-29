package frog.calculator.explain.command;

import frog.calculator.ISymbol;

public interface ICommandFactory extends ISymbol {
    ICommand instance();
}
