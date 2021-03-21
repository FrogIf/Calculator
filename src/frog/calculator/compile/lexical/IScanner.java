package frog.calculator.compile.lexical;

/**
 * 表达式扫描器
 */
public interface IScanner {
    /**
     * 获取当前字符
     */
    char read();

    /**
     * 是否没有结束
     * @return true - 是; false - 否
     */
    boolean isNotEnd();

    /**
     * 移动至下一个
     */
    boolean moveToNext();

    /**
     * 当前字符的索引值
     */
    int position();
}
