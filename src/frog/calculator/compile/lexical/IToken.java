package frog.calculator.compile.lexical;

import frog.calculator.compile.IWord;
import frog.calculator.compile.syntax.ISyntaxNodeGenerator;

/**
 * 词法分析的token
 */
public interface IToken extends IWord {

    /**
     * 获取词法节点生成器
     * @return
     */
    ISyntaxNodeGenerator getSyntaxNodeGenerator();
    
}
