package frog.calculator.compile;

import frog.calculator.compile.exception.BuildException;
import frog.calculator.compile.lexical.IScanner;
import frog.calculator.compile.syntax.ISyntaxNode;

public interface ISyntaxTreeBuilder {

    /**
     * 将表达式构建为语法树
     * @param scanner 表达式扫描器
     * @return 语法树
     */
    ISyntaxNode build(IScanner scanner) throws BuildException;

    /**
     * 将两个节点构建为一棵语法树, 如果两个节点无法组成一棵树, 返回null
     * @param nodeA
     * @param nodeB
     * @return
     */
    ISyntaxNode associate(ISyntaxNode nodeA, ISyntaxNode nodeB, IBuildContext context);

}
