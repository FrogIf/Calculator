package frog.calculator.build.command;

import frog.calculator.build.register.IRegister;

public class DefaultCommandDetector implements ICommandDetector {

    private final IRegister<ICommandFactory> commandRegister;

    public DefaultCommandDetector(IRegister<ICommandFactory> commandRegister) {
        this.commandRegister = commandRegister;
    }

    public ICommand detect(char[] exps, int startIndex){
        ICommandFactory factory = commandRegister.retrieve(exps, startIndex);
        if(factory == null){
            return null;
        }else{
            return factory.instance();
        }
    }

}
