package frog.calculator;

import frog.calculator.command.DeclareCommand;
import frog.calculator.command.ICommand;

public class DefaultCommandHolder implements ICommandHolder {

    private ICalculatorManager calculatorManager;

    private ICalculatorConfigure calculatorConfigure;

    public DefaultCommandHolder(ICalculatorManager calculatorManager, ICalculatorConfigure calculatorConfigure) {
        this.calculatorManager = calculatorManager;
        this.calculatorConfigure = calculatorConfigure;
    }

    @Override
    public ICommand[] getCommands() {
        return new ICommand[]{
                new DeclareCommand("@", "=", calculatorManager, calculatorConfigure.getExpressionHolder().getStructureExpression())
        };
    }
}
