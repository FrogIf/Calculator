package frog.calculator.connect;

import frog.calculator.build.register.IRegister;
import frog.calculator.build.resolve.IResolverResult;
import frog.calculator.exception.DuplicateSymbolException;
import frog.calculator.express.IExpression;

/**
 * 计算器会话<br/>
 * 不同会话之间是完全数据隔离的, 每一次计算都需要使用会话<br/>
 * 单一会话是线程不安全的, 会话之间是线程安全的
 */
public interface ICalculatorSession {

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
     * 添加会话变量
     * @param expression 需要添加的变量表达式
     * @throws DuplicateSymbolException 当在当前变量表中存在该表达式时, 抛出重复符号异常
     */
    void addVariable(IExpression expression) throws DuplicateSymbolException;

    /**
     * 从会话变量中获取值<br />
     * 遵循就近原则
     * @return 返回检索到的最长匹配解析结果
     */
    IResolverResult resolveVariable(char[] expChars, int startIndex);

}
