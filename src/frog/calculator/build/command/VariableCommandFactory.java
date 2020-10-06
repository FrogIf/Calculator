package frog.calculator.build.command;

public class VariableCommandFactory extends AbstractCommandFactory {

    private final String assign;

    public VariableCommandFactory(String symbol, String assign) {
        super(symbol);
        this.assign = assign;
    }

    @Override
    public ICommand instance() {
        return new ValueVariableDeclareCommand(this.symbol, this.assign);
    }
}
