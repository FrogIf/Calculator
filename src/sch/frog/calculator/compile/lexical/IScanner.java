package sch.frog.calculator.compile.lexical;

import sch.frog.calculator.compile.lexical.exception.ReadOutOfBoundsException;

/**
 * 表达式扫描器
 */
public interface IScanner {

    /**
     * 预读当前字符
     * @return
     */
    char peek() throws ReadOutOfBoundsException;

    /**
     * 是否没有结束
     * @return true - 是; false - 否
     */
    boolean isNotEnd();

    /**
     * 移动到下一个位置
     */
    void take() throws ReadOutOfBoundsException;

    /**
     * 当前所处位置
     */
    int position();
}
