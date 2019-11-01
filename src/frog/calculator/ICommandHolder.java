package frog.calculator;

import frog.calculator.command.ICommand;

public interface ICommandHolder {

    ICommand[] getCommands();

}
