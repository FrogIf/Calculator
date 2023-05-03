package sch.frog.calculator.compile.syntax;

import sch.frog.calculator.compile.semantic.exec.IExecutor;
import sch.frog.calculator.util.collection.IList;

/**
 * 不可推演节点, 其左右都没有子树
 */
public final class UndeducibleNode extends AbstractSyntaxNode {

    public UndeducibleNode(String word, IExecutor executor, int position) {
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
    
}
