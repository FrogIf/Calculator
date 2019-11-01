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
    public IExpression getVariable(String varName) {
        Iterator<IRegister<IExpression>> iterator = localRegisterStack.iterator();
        IExpression expression;
        while(iterator.hasNext()){
            IRegister<IExpression> register = iterator.next();
            expression = register.find(varName);
            if(expression != null){
                return expression.clone();
            }
        }
        return null;
    }

    @Override
    public IResolverResult resolveVariable(char[] expChars, int startIndex) {
        Iterator<IRegister<IExpression>> iterator = localRegisterStack.iterator();
        IExpression expression;
        while(iterator.hasNext()){
            IRegister<IExpression> register = iterator.next();
            expression = register.retrieve(expChars, startIndex);
            if(expression != null){
                return manager.createResolverResult(expression);
            }
        }
        return null;
    }

    @Override
    public void pushCommand(ICommand command) {
        this.commandStack.push(command);
    }

    @Override
    public ICommand popCommand() {
        return this.commandStack.pop();
    }

    @Override
    public ITraveller<ICommand> commandTraveller() {
        return this.commandStack.iterator();
    }


}
