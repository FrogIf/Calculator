package frog.calculator.connect;

import frog.calculator.command.ICommand;
import frog.calculator.express.IExpression;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.util.collection.ITraveller;

public interface ICalculatorSession {

    /**
     * 创建局部变量域
     */
    void createLocalVariableRegion();

    /**
     * 添加会话变量
     * @param expression
     */
    void addVariable(IExpression expression);

    /**
     * 从会话变量中获取值
     * @param varName
     * @return
     */
    IExpression getVariable(String varName);

    /**
     * 从会话变量中获取值
     * @return
     */
    IResolverResult resolveVariable(char[] expChars, int startIndex);

    void pushCommand(ICommand command);

    void popCommand(ICommand command);

    ITraveller<ICommand> commandTraveller();
}
