package io.github.frogif.calculator.compile.lexical.fetcher;

import io.github.frogif.calculator.compile.lexical.IScanner;
import io.github.frogif.calculator.compile.lexical.IToken;

/**
 * token获取
 */
public interface ITokenFetcher {
    
    /**
     * 通过指定的scanner获取token
     * @param scanner 表达式扫描器
     * @return 返回token, 如果获取不到, 返回null
     */
    IToken fetch(IScanner scanner);

}
