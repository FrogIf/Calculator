package frog.calculator.connect;

import frog.calculator.ICalculatorManager;
import frog.calculator.build.register.IRegister;
import frog.calculator.build.register.SymbolRegister;
import frog.calculator.build.resolve.IResolverResult;
import frog.calculator.exception.DuplicateSymbolException;
import frog.calculator.express.IExpression;
import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.Stack;

public class DefaultCalculatorSession extends AbstractCalculatorSession {

    private ICalculatorManager manager;

    private SymbolRegister<IExpression> sessionRegister = new SymbolRegister<>();

    private Stack<IRegister<IExpression>> localRegisterStack = new Stack<>(); // 变量栈, 栈底是session全局变量, 其余是局部变量

    {
        localRegisterStack.add(sessionRegister);   // session根栈, 存储会话变量, 不能删除
    }

    public DefaultCalculatorSession(ICalculatorManager manager) {
        this.manager = manager;
    }

    @Override
    public void createLocalVariableTable() {
        localRegisterStack.push(new SymbolRegister<>());
    }

    @Override
    public IRegister<IExpression> popLocalVariableTable() {
        IRegister<IExpression> pop = localRegisterStack.pop();
        if(pop == sessionRegister){
            localRegisterStack.push(sessionRegister);
            throw new IllegalStateException("no local variable region can be pop.");
        }
        return pop;
    }

    @Override
    public void addVariable(IExpression expression) throws DuplicateSymbolException {
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

}
