package frog.calculator.build.command;

import frog.calculator.ICalculatorManager;
import frog.calculator.express.IExpressionHolder;

public class VariableCommandFactory extends AbstractCommandFactory {

    private ICalculatorManager calculatorManager;

    private IExpressionHolder holder;

    public VariableCommandFactory(String symbol, ICalculatorManager calculatorManager, IExpressionHolder holder) {
        super(symbol);
        this.calculatorManager = calculatorManager;
        this.holder = holder;
    }

    @Override
    public ICommand instance() {
        return new VariableDeclareCommand(this.symbol, this.calculatorManager, this.holder);
    }
}
