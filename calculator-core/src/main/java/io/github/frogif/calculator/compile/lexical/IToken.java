package io.github.frogif.calculator.compile.lexical;

import io.github.frogif.calculator.compile.IWord;
import io.github.frogif.calculator.compile.syntax.ISyntaxNodeGenerator;

/**
 * 词法分析的token
 */
public interface IToken extends IWord {

    /**
     * 获取语法节点生成器
     * @return 节点生成器
     */
    ISyntaxNodeGenerator getSyntaxNodeGenerator();

    /**
     * token在表达式中的位置
     * @return 位置
     */
    int position();
}
