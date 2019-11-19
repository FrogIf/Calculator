package frog.calculator.connect;

import frog.calculator.build.IExpressionBuilder;
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

    /**
     * 获取当前会话的表达式构建器
     * @return 构建器builder
     */
    IExpressionBuilder getExpressionBuilder();

    /**
     * 删除指定的变量
     * @param symbol 待删除的变量的符号
     * @return 已删除的变量表达式对象
     */
    boolean removeVariable(String symbol);

}
