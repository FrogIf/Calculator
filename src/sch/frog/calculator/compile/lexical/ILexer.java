package sch.frog.calculator.compile.lexical;

import sch.frog.calculator.compile.lexical.exception.UnrecognizedTokenException;

/**
 * 词法解析器
 */
public interface ILexer {

    /**
     * 词法解析
     * @param scanner 表达式扫描器
     * @return 返回解析结果
     * @throws UnrecognizedTokenException 如果无法进行词法解析, 将抛出此异常
     */
    IToken parse(IScannerOperator operator) throws UnrecognizedTokenException;
    
}
