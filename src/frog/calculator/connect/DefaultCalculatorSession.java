package frog.calculator.connect;

import frog.calculator.express.IExpression;
import frog.calculator.register.IRegister;
import frog.calculator.register.SymbolRegister;
import frog.calculator.util.collection.Stack;

public class DefaultCalculatorSession implements ICalculatorSession {

    private SymbolRegister register = new SymbolRegister();

    private Stack<IRegister> localRegisterStack = new Stack<>(); // 局部注册器栈, 用于存储局部变量

    {
        localRegisterStack.add(register);   // session根栈, 存储会话变量, 不能删除
    }

    public DefaultCalculatorSession() { }

    @Override
    public void addVariable(IExpression expression) {
        register.insert(expression);
    }

    @Override
    public IExpression getVariable(String varName) {
        IExpression expression = register.find(varName);
        if(expression != null){ expression = expression.clone(); }
        return expression;
    }

    @Override
    public IExpression getVariable(char[] expChars, int startIndex) {
        IExpression expression = register.retrieve(expChars, startIndex);
        if(expression != null){ expression = expression.clone();}
        return expression;
    }


}
