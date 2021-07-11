package frog.calculator.compile.syntax;

import frog.calculator.compile.semantic.exec.IExecutor;
import frog.calculator.util.collection.IList;

/**
 * 不可推演节点, 其左右都没有子树
 */
public final class UndeducibleNode extends AbstractSyntaxNode implements ISyntaxNodeGenerator {

    public UndeducibleNode(String word, int position, IExecutor executor) {
        super(word, -1, executor);
        this.position = position;
    }

    public UndeducibleNode(String word, IExecutor executor) {
        super(word, -1, executor);
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
        return new UndeducibleNode(this.word, position, this.executor);
    }
    
}
