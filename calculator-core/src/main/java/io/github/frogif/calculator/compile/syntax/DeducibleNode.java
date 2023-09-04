package io.github.frogif.calculator.compile.syntax;

import io.github.frogif.calculator.compile.semantic.exec.IExecutor;
import io.github.frogif.calculator.util.collection.ArrayList;
import io.github.frogif.calculator.util.collection.IList;

/**
 * 可推演节点, 左侧和右侧都只有一个子节点
 */
public class DeducibleNode extends AbstractSyntaxNode {

    private ISyntaxNode leftChild;

    private ISyntaxNode rightChild;

    private final boolean leftOpen;

    private final boolean rightOpen;

    public DeducibleNode(String word, int priority, IExecutor executor, int position) {
        this(word, priority, AssociateType.ALL, executor, position);
    }

    public DeducibleNode(String word, int priority, AssociateType associateType, IExecutor executor, int position) {
        super(word, priority, executor);
        if(associateType == null){
            throw new IllegalArgumentException("associate type is null.");
        }
        this.leftOpen = (associateType.score & 1) > 0;
        this.rightOpen = (associateType.score & 2) > 0;
        this.position = position;
    }

    @Override
    public boolean isLeftOpen() {
        return this.leftOpen;
    }

    @Override
    public boolean isRightOpen() {
        return this.rightOpen;
    }

    @Override
    public boolean branchOff(ISyntaxNode child, IAssembler assembler) {
        ISyntaxNode root;

        if(this.leftOpen && child.position() < this.position){
            if(leftChild == null){
                this.leftChild = child;
                return true;
            }else{
                root = assembler.associate(this.leftChild, child);
                if(root == null){ return false; }
                this.leftChild = root;
            }
            return true;
        }

        if(this.rightOpen){
            if(rightChild == null){
                this.rightChild = child;
            }else{
                root = assembler.associate(this.rightChild, child);
                if(root == null){ return false; }
                this.rightChild = root;
            }
            return true;
        }

        return false;
    }

    @Override
    public IList<ISyntaxNode> children() {
        ArrayList<ISyntaxNode> children = new ArrayList<>();
        if(this.leftOpen){
            children.add(this.leftChild);
        }
        if(this.rightOpen){
            children.add(this.rightChild);
        }
        return children;
    }

    public enum AssociateType{
        /**
         * 左结合
         */
        LEFT(1),
        /**
         * 右结合
         */
        RIGHT(2),
        /**
         * 左右都可以结合
         */
        ALL(3);

        private final int score;

        AssociateType(int score){
            this.score = score;
        }
    }
    
}
