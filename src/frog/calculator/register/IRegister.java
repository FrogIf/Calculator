package frog.calculator.register;

import frog.calculator.express.IExpression;

public interface IRegister {

    void insert(IExpression expression);

    IExpression find(String symbol);

    IExpression retrieve(char[] chars, int startIndex);

    boolean remove(String exp);

    void replace(String exp, IExpression expression);

    /**
     * 判断当前表达式是否没有记录任何表达式
     * @return
     */
    boolean isEmpty();
}
