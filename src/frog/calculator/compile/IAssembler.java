package frog.calculator.compile;

import frog.calculator.compile.syntax.ISyntaxNode;

public interface IAssembler {

    /**
     * 将两个节点构建为一棵语法树, 如果两个节点无法组成一棵树, 返回null
     * @param nodeA
     * @param nodeB
     * @return
     */
    ISyntaxNode associate(ISyntaxNode nodeA, ISyntaxNode nodeB);
    
}
