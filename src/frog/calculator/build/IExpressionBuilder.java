package frog.calculator.build;

import frog.calculator.build.command.ICommand;
import frog.calculator.build.region.IBuildPipe;
import frog.calculator.build.register.IRegister;
import frog.calculator.build.resolve.IResolverResult;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.exception.BuildException;
import frog.calculator.exception.DuplicateSymbolException;
import frog.calculator.express.IExpression;

public interface IExpressionBuilder {

    IExpression getRoot();

    void addBuildFinishListener(IBuildFinishListener listener);

    void setBuildPipe(IBuildPipe pipe);

    ICalculatorSession getSession();

    /**
     * 向会话中添加command
     * @param command 待添加的命令
     */
    void pushCommand(ICommand command);

    /**
     * 销毁会话顶部命令<br/>
     * 为安全起见, 必须传入待销毁命令本身方可销毁该命令
     * @param command 指定待销毁的命令
     */
    void popCommand(ICommand command);

    void addVariable(IExpression expression) throws DuplicateSymbolException;

    /**
     * 创建局部变量表
     */
    void createLocalVariableTable();

    /**
     * 销毁栈顶局部变量表
     * @return 栈顶局部变量表
     */
    IRegister<IExpression> popLocalVariableTable();

    /**
     * 从会话变量中获取值<br />
     * 遵循就近原则
     * @return 返回检索到的最长匹配解析结果
     */
    IResolverResult resolveVariable(char[] expChars, int startIndex);

    IExpression build(String expression) throws BuildException;

}
