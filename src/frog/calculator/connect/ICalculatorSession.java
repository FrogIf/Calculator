package frog.calculator.connect;

import frog.calculator.command.ICommand;
import frog.calculator.exception.DuplicateSymbolException;
import frog.calculator.express.IExpression;
import frog.calculator.register.IRegister;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.util.collection.ITraveller;

public interface ICalculatorSession {

    /**
     * 创建局部变量域
     */
    void createLocalVariableRegion();

    IRegister<IExpression> popLocalVariableRegion();

    /**
     * 创建瞬时变量表<br/>
     * 如果已经存在瞬时变量表, 则直接覆盖
     */
    void setTransientVariableRegion(IRegister<IExpression> expression);

    /**
     * 获取并销毁瞬时变量表
     * @return 返回瞬时变量表
     */
    IRegister<IExpression> fetchTransientVariableRegion();


    /**
     * 添加会话变量
     * @param expression
     */
    void addVariable(IExpression expression) throws DuplicateSymbolException;

    /**
     * 从会话变量中获取值
     * @return
     */
    IResolverResult resolveVariable(char[] expChars, int startIndex);

    void pushCommand(ICommand command);

    void popCommand(ICommand command);

    void clearCommand();

    ITraveller<ICommand> commandTraveller();
}
