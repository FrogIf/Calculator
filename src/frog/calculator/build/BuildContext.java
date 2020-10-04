package frog.calculator.build;

import frog.calculator.connect.ICalculatorSession;
import frog.calculator.express.IExpression;

public class BuildContext implements IBuildContext {

    private IExpression root;

    private final ICalculatorSession session;

    private final CommandChain commandChain;

    private final VariableTableManager variableTableManager = new VariableTableManager();

    BuildContext(ICalculatorSession session, CommandChain commandChain) {
        this.session = session;
        this.commandChain = commandChain;
    }

    void setRoot(IExpression root){
        this.root = root;
    }

    @Override
    public IExpression getRoot() {
        return root;
    }

    @Override
    public void addListener(IBuildFinishListener listener) {

    }

    @Override
    public ICalculatorSession getSession() {
        return this.session;
    }

    @Override
    public CommandChain commandChain() {
        return this.commandChain;
    }

    @Override
    public IVariableTableManager getLocalVariableTableManager() {
        return this.variableTableManager;
    }

}
