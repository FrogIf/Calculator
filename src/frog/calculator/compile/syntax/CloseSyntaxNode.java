package frog.calculator.compile.syntax;

import frog.calculator.compile.IBuildContext;
import frog.calculator.compile.semantic.IExecutor;
import frog.calculator.util.collection.IList;

/**
 * 关闭表达式, 其左右都没有子树
 */
public final class CloseSyntaxNode extends AbstractSyntaxNode implements ISyntaxNodeBuilder {

    public CloseSyntaxNode(IExecutor executor, String word, int position) {
        super(executor, word, -1);
        this.position = position;
    }

    @Override
    public boolean isOpen() {
        // close syntax is always close
        return false;
    }

    @Override
    public boolean branchOff(ISyntaxNode child, IBuildContext context) {
        // close syntax node can't branch off.
        return false;
    }

    @Override
    public IList<ISyntaxNode> children() {
        // close syntax node doesn't children.
        return null;
    }

    @Override
    public ISyntaxNode build(int position, IBuildContext context) {
        return new CloseSyntaxNode(this.executor, this.word, position);
    }
    
}
