package io.github.frogif.calculator.compile.syntax;

import io.github.frogif.calculator.compile.semantic.exec.IExecutor;
import io.github.frogif.calculator.util.collection.ArrayList;
import io.github.frogif.calculator.util.collection.IList;

/**
 * 具有动态结合性节点的
 * 需要指定策略来判断结合性
 * 只有右结合性可以是动态的, 左结合性不支持动态
 */
public class DynamicAssociativityNode extends AbstractSyntaxNode {

    private ISyntaxNode leftChild;

    private ISyntaxNode rightChild;

    private final IAssociativity rightAssociativity;

    private final boolean leftOpen;

    private boolean rightOpen = true;

    public DynamicAssociativityNode(String word, int priority, boolean leftOpen, IAssociativity rightAssociativity, IExecutor executor, int position) {
        super(word, priority, executor);
        this.leftOpen = leftOpen;
        this.rightAssociativity = rightAssociativity;
        if(this.rightAssociativity == null){
            throw new IllegalArgumentException("right associativity is required");
        }
        this.position = position;
    }

    @Override
    public boolean isLeftOpen() {
        return leftOpen;
    }

    @Override
    public boolean isRightOpen() {
        return rightOpen;
    }

    @Override
    public boolean branchOff(ISyntaxNode child, IAssembler assembler) {
        String word = child.word();
        ISyntaxNode root;

        if(this.leftOpen && child.position() < this.position){
            if(leftChild == null){
                this.leftChild = child;
            }else{
                root = assembler.associate(this.leftChild, child);
                if(root == null){ return false; }
                this.leftChild = root;
            }
            return true;
        }

        if(this.rightOpen && (this.rightOpen = this.rightAssociativity.peek(word))){
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
        if(leftOpen){
            children.add(leftChild);
        }
        if(this.rightChild != null){
            children.add(rightChild);
        }
        return children;
    }

    public interface IAssociativity{
        boolean peek(String word);

        IAssociativity copy();
    }
}
