package frog.calculator.command;

public interface ICommandDetector {

    ICommand detect(char[] exps, int startIndex);

}
