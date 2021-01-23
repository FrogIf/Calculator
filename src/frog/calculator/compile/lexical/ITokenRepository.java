package frog.calculator.compile.lexical;

import frog.calculator.compile.lexical.exception.DuplicateTokenException;
import frog.calculator.util.collection.IList;

/**
 * token 存储
 */
public interface ITokenRepository {

    /**
     * 插入指定的token对象
     * @param token 带插入的token
     * @throws DuplicateTokenException 如果该token已存在, 则抛出DuplicateTokenException异常
     */
    void insert(IToken token) throws DuplicateTokenException;

    /**
     * 移除指定的token
     * @param word token的符号
     * @return 是否成功移除, 只有要移除的token并不存在时, 返回false
     */
    boolean remove(String word);

    /**
     * 使用新的token替换原来的token
     * @param word 替换的目标符号
     * @param token 新的token
     */
    void replace(String word, IToken token);

    /**
     * 检索获取与输入字符数组匹配的最长符号对象
     * @param scanner 表达式扫描器
     * @return 匹配结果, 如果没有匹配结果, 则返回null
     */
    IToken retrieve(IScanner scanner);

    /**
     * 判断是否为空
     * @return true 是; false 否
     */
    boolean isEmpty();

    /**
     * 获取存储的所有token
     * @return 所有token
     */
    IList<IToken> getTokens();
    
}
