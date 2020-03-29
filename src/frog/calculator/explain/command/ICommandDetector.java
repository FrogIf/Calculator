package frog.calculator.explain.command;

public interface ICommandDetector {

    ICommand detect(char[] exps, int startIndex);

}
