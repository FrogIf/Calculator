package frog.calculator.compile.syntax;

import frog.calculator.compile.IWord;

/**
 * syntax node生成器
 */
public interface ISyntaxNodeGenerator extends IWord {

    /**
     * 构建需要参数position, context, 这两者将用于后续的语法分析以及语义分析中
     * @param position 在整个表达式中的位置
     * @return 构建出的syntax node
     */
    ISyntaxNode generate(int position);
    
}
