package frog.calculator.build;

import frog.calculator.ICalculatorContext;
import frog.calculator.build.command.ICommand;
import frog.calculator.build.register.IRegister;
import frog.calculator.exception.BuildException;
import frog.calculator.exception.DuplicateSymbolException;
import frog.calculator.express.IExpression;

public interface IExpressionBuilder {

    /**
     * 获取表达式的根节点
     * @return 根节点
     */
    IExpression getRoot();

    /**
     * 注册构建完成监听
     * @param listener 构建完成监听
     */
    void addBuildFinishListener(IBuildFinishListener listener);

    /**
     * 向会话中添加command
     * @param command 待添加的命令
     */
//    void pushCommand(ICommand command);

    /**
     * 销毁会话顶部命令<br/>
     * 为安全起见, 必须传入待销毁命令对象本身方可销毁该命令
     * @param command 指定的待销毁的命令对象
     */
//    void popCommand(ICommand command);

    /**
     * 通过构建器添加变量, 如果存在局部变量表, 会添加到顶层局部变量表中, 如果没有, 会添加到session变量表中
     * @param expression 待添加的变量
     * @throws DuplicateSymbolException 存在重复变量
     */
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
     * 将指定的字符串解析为表达式树
     * @param expression 待解析的字符串
     * @return 解析结果
     * @throws BuildException 构建失败
     */
    IExpression build(char[] expression) throws BuildException;

    void viewCalculatorContext(ICalculatorContext context);

}
