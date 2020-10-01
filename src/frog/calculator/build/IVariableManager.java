package frog.calculator.build;

import frog.calculator.build.resolve.IResolver;
import frog.calculator.exception.DuplicateSymbolException;
import frog.calculator.express.IExpression;

public interface IVariableManager extends IResolver {

    /**
     * 添加会话变量
     * @param expression 需要添加的变量表达式
     * @throws DuplicateSymbolException 当在当前变量表中存在该表达式时, 抛出重复符号异常
     */
    void addVariable(IExpression expression) throws DuplicateSymbolException;

    /**
     * 删除指定的变量
     * @param symbol 待删除的变量的符号
     * @return 已删除的变量表达式对象
     */
    boolean removeVariable(String symbol);

}
