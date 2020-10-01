package frog.calculator.build;

import frog.calculator.build.register.IRegister;
import frog.calculator.express.IExpression;

public interface IVariableTableManager extends IVariableManager {

    void createLocalVariableTable();

    IRegister<IExpression> popLocalVariableTable();

    /**
     * 指示是否没有变量表
     * @return
     */
    boolean isEmpty();

}
