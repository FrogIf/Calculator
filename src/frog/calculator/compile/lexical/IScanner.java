package frog.calculator.compile.lexical;

/**
 * 表达式扫描器
 */
public interface IScanner {
    /**
     * 当前字符
     */
    char current();

    /**
     * 是否有下一个
     * @return true - 有; false - 无
     */
    boolean hasNext();

    /**
     * 获取下一个字符
     */
    char next();

    /**
     * 当前字符的索引值
     */
    int position();
}
