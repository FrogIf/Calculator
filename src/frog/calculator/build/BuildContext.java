package frog.calculator.build;

import frog.calculator.build.resolve.IResolverResultFactory;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.express.IExpression;

public class BuildContext implements IBuildContext {

    private IExpression root;

    private final ICalculatorSession session;

    private final CommandChain commandChain;

    private final VariableTableManager variableTableManager;

    BuildContext(ICalculatorSession session, CommandChain commandChain, IResolverResultFactory resolveResultFactory) {
        this.session = session;
        this.commandChain = commandChain;
        this.variableTableManager = new VariableTableManager(resolveResultFactory);
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
