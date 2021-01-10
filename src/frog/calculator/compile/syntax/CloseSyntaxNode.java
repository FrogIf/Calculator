package frog.calculator.compile.syntax;

import frog.calculator.compile.semantic.IExecutor;
import frog.calculator.util.collection.IList;

/**
 * 关闭表达式, 其左右都没有子树
 */
public final class CloseSyntaxNode extends AbstractSyntaxNode implements ISyntaxNodeBuilder {

    public CloseSyntaxNode(IExecutor executor, String literal) {
        super(executor, literal, -1);
    }

    @Override
    public boolean isOpen() {
        // close syntax is always close
        return false;
    }

    @Override
    public boolean branchOff(ISyntaxNode child) {
        // close syntax node can't branch off.
        return false;
    }

    @Override
    public IList<ISyntaxNode> children() {
        // close syntax node doesn't children.
        return null;
    }

    @Override
    public ISyntaxNode build(int position, ISyntaxTreeContext context) {
        CloseSyntaxNode node = new CloseSyntaxNode(this.executor, this.literal);
        node.position = position;
        node.context = context;
        return node;
    }
    
}
