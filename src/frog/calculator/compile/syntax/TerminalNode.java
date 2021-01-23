package frog.calculator.compile.syntax;

import frog.calculator.compile.semantic.IExecutor;
import frog.calculator.util.collection.IList;

/**
 * 终结节点, 其左右都没有子树
 */
public final class TerminalNode extends AbstractSyntaxNode implements ISyntaxNodeGenerator {

    public TerminalNode(IExecutor executor, String word, int position) {
        super(executor, word, -1);
        this.position = position;
    }

    public TerminalNode(IExecutor executor, String word) {
        super(executor, word, -1);
    }

    @Override
    public boolean isLeftOpen() {
        // terminal syntax is always close
        return false;
    }

    @Override
    public boolean isRightOpen() {
        // terminal syntax is always close
        return false;
    }

    @Override
    public boolean branchOff(ISyntaxNode child, IAssembler assembler) {
        // terminal syntax node can't branch off.
        return false;
    }

    @Override
    public IList<ISyntaxNode> children() {
        // terminal syntax node doesn't children.
        return null;
    }

    @Override
    public ISyntaxNode generate(int position) {
        return new TerminalNode(this.executor, this.word, position);
    }
    
}
