package frog.calculator.compile.lexical.fetcher;

import frog.calculator.compile.lexical.IScanner;
import frog.calculator.compile.lexical.IToken;

public interface ITokenFetcher {
    
    /**
     * 通过指定的scanner获取token
     * @param scanner 表达式扫描器
     * @return 返回token, 如果获取不到, 返回null
     */
    IToken fetch(IScanner scanner);

    /**
     * 获取优先级, 优先级高的先执行
     * @return 返回一个数字, 数字越小, 优先级越高
     */
    int order();

    /**
     * 是否可以被接下来的fetcher覆盖
     * @return
     */
    boolean overridable();
}
