package frog.calculator.build.command;

import frog.calculator.ICalculatorConfigure;
import frog.calculator.ICalculatorManager;
import frog.calculator.express.IExpressionHolder;

public class DefaultCommandHolder implements ICommandHolder {

    private ICalculatorManager calculatorManager;

    private ICalculatorConfigure calculatorConfigure;

    public DefaultCommandHolder(ICalculatorManager calculatorManager, ICalculatorConfigure calculatorConfigure) {
        this.calculatorManager = calculatorManager;
        this.calculatorConfigure = calculatorConfigure;
    }

    @Override
    public ICommand[] getCommands() {
        IExpressionHolder holder = calculatorConfigure.getComponentFactory().createExpressionHolder();
        return new ICommand[]{
                new VariableDeclareCommand("@", calculatorManager, holder),
                new BlockCommand(holder)
        };
    }
}
