package frog.calculator.compile.syntax;

import frog.calculator.compile.IAssembler;
import frog.calculator.compile.semantic.IExecutor;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;

/**
 * 非终结节点, 左侧和右侧都只有一个子节点, 并且open状态永远是true
 */
public class NonterminalNode extends AbstractSyntaxNode implements ISyntaxNodeGenerator {

    private ISyntaxNode leftChild;

    private ISyntaxNode rightChild;

    private final boolean leftOpen;

    private final boolean rightOpen;

    private final AssociateType associateType;

    public NonterminalNode(IExecutor executor, String word, int priority) {
        this(executor, word, priority, AssociateType.ALL);
    }

    public NonterminalNode(IExecutor executor, String literal, int priority, AssociateType associateType) {
        super(executor, literal, priority);
        if(associateType == null){
            throw new IllegalArgumentException("assocaite type is null.");
        }
        this.leftOpen = (associateType.score & 1) > 0;
        this.rightOpen = (associateType.score & 2) > 0;
        this.associateType = associateType;
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
        ISyntaxNode root = null;

        if(this.leftOpen && child.position() < this.position){
            if(leftChild == null){
                this.leftChild = child;
                return true;
            }
            root = assembler.associate(this.leftChild, child);
            if(root != null){
                this.leftChild = root;
                return true;
            }
        }

        if(this.rightOpen){
            if(rightChild == null){
                this.rightChild = child;
                return true;
            }
            root = assembler.associate(this.rightChild, child);
            if(root != null){
                this.rightChild = root;
                return true;
            }
        }

        return false;
    }

    @Override
    public IList<ISyntaxNode> children() {
        return new ArrayList<>(new ISyntaxNode[]{this.leftChild, this.rightChild});
    }

    @Override
    public ISyntaxNode generate(int position) {
        NonterminalNode node = new NonterminalNode(this.executor, this.word, this.priority, this.associateType);
        node.position = position;
        return node;
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

        private AssociateType(int score){
            this.score = score;
        }
    }
    
}
