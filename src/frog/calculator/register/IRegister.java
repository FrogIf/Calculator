package frog.calculator.register;

import frog.calculator.express.IExpression;
import frog.calculator.operator.IOperator;

public interface IRegister {

    void registe(String exp, IExpression expression, IOperator operator);

    void registe(String exp, IOperator operator);

    void registe(String exp, IExpression expression);

    IExpression find(String symbol);

    IExpression retrieveRegistryInfo(char[] chars, int startIndex);

    boolean remove(String exp);

    void replace(String exp, IExpression expression, IOperator operator);

    /**
     * 判断当前表达式是否没有记录任何表达式
     * @return
     */
    boolean isEmpty();
}
