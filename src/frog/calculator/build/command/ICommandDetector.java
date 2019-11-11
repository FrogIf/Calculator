package frog.calculator.build.command;

public interface ICommandDetector {

    ICommand detect(char[] exps, int startIndex);

}
