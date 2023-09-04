package io.github.frogif.calculator.compile.syntax;

/**
 * syntax node生成器
 */
public interface ISyntaxNodeGenerator {

    /**
     * 构建需要参数position, context, 这两者将用于后续的语法分析以及语义分析中
     * @param word 词
     * @param position 在整个表达式中的位置
     * @return 构建出的syntax node
     */
    ISyntaxNode generate(String word, int position);
    
}
