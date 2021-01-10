package frog.calculator.compile.lexical;

/**
 * 词法解析器
 */
public interface ILexer {

    /**
     * 词法解析
     * @param scanner 表达式扫描器
     * @return 返回解析结果, 如果解析失败, 返回null
     */
    IToken parse(IScanner scanner);
    
}
