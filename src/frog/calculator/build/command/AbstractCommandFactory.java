package frog.calculator.build.command;

public abstract class AbstractCommandFactory implements ICommandFactory{

    protected String symbol;

    public AbstractCommandFactory(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String symbol() {
        return this.symbol;
    }
}
