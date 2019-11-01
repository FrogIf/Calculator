package frog.calculator.command;

import frog.calculator.register.IRegister;

public class CommandDetector {

    private final IRegister<ICommand> commandRegister;

    public CommandDetector(IRegister<ICommand> commandRegister) {
        this.commandRegister = commandRegister;
    }

    public ICommand detect(char[] exps, int startIndex){
        return commandRegister.retrieve(exps, startIndex);
    }

}
