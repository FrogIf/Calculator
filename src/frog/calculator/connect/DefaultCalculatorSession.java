package frog.calculator.connect;

import frog.calculator.ICalculatorManager;
import frog.calculator.command.ICommand;
import frog.calculator.express.IExpression;
import frog.calculator.register.IRegister;
import frog.calculator.register.SymbolRegister;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.util.collection.ITraveller;
import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.Stack;

public class DefaultCalculatorSession implements ICalculatorSession {

    private ICalculatorManager manager;

    private SymbolRegister<IExpression> sessionRegister = new SymbolRegister<>();

    private Stack<ICommand> commandStack = new Stack<>();   // 会话命令栈

    private Stack<IRegister<IExpression>> localRegisterStack = new Stack<>(); // 变量栈, 栈底是session全局变量, 其余是局部变量

    {
        localRegisterStack.add(sessionRegister);   // session根栈, 存储会话变量, 不能删除
    }

    public DefaultCalculatorSession(ICalculatorManager manager) {
        this.manager = manager;
    }

    @Override
    public void createLocalVariableRegion() {
        localRegisterStack.push(new SymbolRegister<>());
    }

    @Override
    public void addVariable(IExpression expression) {
        localRegisterStack.first().insert(expression);
    }

    @Override
    public IResolverResult resolveVariable(char[] expChars, int startIndex) {
        Iterator<IRegister<IExpression>> iterator = localRegisterStack.iterator();
        IExpression expression;
        while(iterator.hasNext()){
            IRegister<IExpression> register = iterator.next();
            expression = register.retrieve(expChars, startIndex);
            if(expression != null){
                IExpression clone = expression.clone();
                return manager.createResolverResult(clone);
            }
        }
        return null;
    }

    @Override
    public void pushCommand(ICommand command) {
        this.commandStack.push(command);
    }

    @Override
    public void popCommand(ICommand command) {
        ICommand pop = this.commandStack.pop();
        if(pop != command){
            this.commandStack.push(pop);
            throw new IllegalStateException("assign command is not in stack top.");
        }
    }

    @Override
    public void clearCommand() {
        this.commandStack.clear();
    }

    @Override
    public ITraveller<ICommand> commandTraveller() {
        return this.commandStack.iterator();
    }


}
