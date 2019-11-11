package frog.calculator.build.register;

import frog.calculator.ISymbol;
import frog.calculator.exception.DuplicateSymbolException;

public interface IRegister<T extends ISymbol> {

    void insert(T expression) throws DuplicateSymbolException;

    T find(String symbol);

    /**
     * 从指定位置向后检索, 获取与输入字符数组匹配的最长符号对象
     * @param chars 待匹配的字符数组
     * @param startIndex 匹配起始位置
     * @return 匹配结果
     */
    T retrieve(char[] chars, int startIndex);

    boolean remove(String exp);

    void replace(String exp, T expression);

    /**
     * 判断当前注册器是否没有记录任何表达式
     * @return true 是; false 否
     */
    boolean isEmpty();
}
