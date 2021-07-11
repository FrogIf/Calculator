package frog.calculator.compile.syntax;

import frog.calculator.compile.semantic.exec.IExecutor;
import frog.calculator.compile.syntax.exception.SyntaxException;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;

/**
 * 可推演节点, 左侧和右侧都只有一个子节点
 */
public class DeducibleNode extends AbstractSyntaxNode implements ISyntaxNodeGenerator {

    private ISyntaxNode leftChild;

    private ISyntaxNode rightChild;

    private final boolean leftOpen;

    private boolean rightOpen;

    private final AssociateType associateType;

    private String end;

    private String next;

    public DeducibleNode(String word, int priority, IExecutor executor) {
        this(word, priority, AssociateType.ALL, executor);
    }

    public DeducibleNode(String literal, int priority, AssociateType associateType, IExecutor executor) {
        super(literal, priority, executor);
        if(associateType == null){
            throw new IllegalArgumentException("assocaite type is null.");
        }
        this.leftOpen = (associateType.score & 1) > 0;
        this.rightOpen = (associateType.score & 2) > 0;
        this.associateType = associateType;
    }

    public DeducibleNode setEnd(String end){
        if(this.end != null){
            throw new IllegalStateException("the end has been assign.");
        }
        this.end = end;
        return this;
    }

    public DeducibleNode setNextRequired(String next){
        if(this.next != null){
            throw new IllegalStateException("the end has been assign.");
        }
        this.next = next;
        return this;
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
            // 如果指定了next, 那么该节点的下一个节点必须是next
            if(this.next != null){
                if(this.position + 1 == child.position() && this.next.equals(child.word())){
                    this.rightOpen = false;
                }else{
                    throw new SyntaxException(child.word(), child.position());
                }
            }
            this.rightOpen = this.end == null || !this.end.equals(child.word());
            if(!this.rightOpen) {
                if(this.rightChild != null){
                    if(!child.branchOff(this.rightChild, assembler)){
                        return false;
                    }
                    this.rightChild = child;
                }
                return true;
            }

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
        if(AssociateType.ALL == this.associateType){
            return new ArrayList<>(new ISyntaxNode[]{this.leftChild, this.rightChild});
        }else if(AssociateType.LEFT == this.associateType){
            return new ArrayList<>(new ISyntaxNode[]{this.leftChild});
        }else{
            return new ArrayList<>(new ISyntaxNode[]{this.rightChild});
        }
    }

    @Override
    public ISyntaxNode generate(int position) {
        DeducibleNode node = new DeducibleNode(this.word, this.priority, this.associateType, this.executor);
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
