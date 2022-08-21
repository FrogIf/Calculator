package sch.frog.calculator.compile.syntax;

/**
 * 语法节点组装者, 将两个语法节点按照语法规则组装成树
 */
public interface IAssembler {

    /**
     * 将两个节点构建为一棵语法树, 如果两个节点无法组成一棵树, 返回null
     * @param nodeA 节点A
     * @param nodeB 节点B
     * @return 组装后的树的根节点, 如果返回null, 说明组装失败
     */
    ISyntaxNode associate(ISyntaxNode nodeA, ISyntaxNode nodeB);
    
}
