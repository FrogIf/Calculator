package frog.calculator.compile.syntax;

import frog.calculator.compile.IWord;

/**
 * syntax node构建者
 */
public interface ISyntaxNodeGenerator extends IWord {

    /**
     * 构建需要参数order, context, 这两者将用于后续的语法分析以及语义分析中
     * @param order 在整个表达式中的顺序
     * @return 构建出的syntax node
     */
    ISyntaxNode generate(int order);
    
}
