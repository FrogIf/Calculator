package frog.calculator.compile.syntax;

/**
 * syntax node构建者
 */
public interface ISyntaxNodeBuilder {

    /**
     * 构建需要参数order, context, 这两者将用于后续的语法分析以及语义分析中
     * @param order 在整个表达式中的顺序
     * @param context 语法树上下文
     * @return 构建出的syntax node
     */
    ISyntaxNode build(int order, ISyntaxTreeContext context);
    
}
