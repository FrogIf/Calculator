package frog.calculator.build;

import frog.calculator.connect.ICalculatorSession;
import frog.calculator.express.IExpression;

/**
 * 构建上下文
 */
public interface IBuildContext {

    IExpression getRoot();

    void addListener(IBuildFinishListener listener);

    ICalculatorSession getSession();

    CommandChain commandChain();

    IVariableTableManager getLocalVariableTableManager();

}
