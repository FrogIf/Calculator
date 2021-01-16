package frog.calculator.compile.lexical;

/**
 * 表达式扫描器
 */
public interface IScanner {
    /**
     * 当前字符
     */
    char peek();

    /**
     * 是否有下一个
     * @return true - 有; false - 无
     */
    boolean hasNext();

    /**
     * 获取当前字符, 并移动至下一个
     */
    char read();

    /**
     * 当前字符的索引值
     */
    int position();
}
