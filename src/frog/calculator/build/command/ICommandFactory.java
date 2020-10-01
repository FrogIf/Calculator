package frog.calculator.build.command;

import frog.calculator.ISymbol;

public interface ICommandFactory extends ISymbol {
    ICommand instance();
}
