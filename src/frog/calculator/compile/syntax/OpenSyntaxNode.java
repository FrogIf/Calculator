package frog.calculator.compile.syntax;

import frog.calculator.compile.IBuildContext;
import frog.calculator.compile.semantic.IExecutor;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;

/**
 * open syntax node, 左侧和右侧都只有一个子节点, 并且open状态永远是true
 */
public class OpenSyntaxNode extends AbstractSyntaxNode implements ISyntaxNodeBuilder {

    private ISyntaxNode leftChild;

    private ISyntaxNode rightChild;

    private final AssociateType associateType;

    public OpenSyntaxNode(IExecutor executor, String word, int priority) {
        this(executor, word, priority, AssociateType.ALL);
    }

    public OpenSyntaxNode(IExecutor executor, String literal, int priority, AssociateType associateType) {
        super(executor, literal, priority);
        if(associateType == null){
            throw new IllegalArgumentException("assocaite type is null.");
        }
        this.associateType = associateType;
    }

    @Override
    public boolean isOpen() {
        return true;
    }

    @Override
    public boolean branchOff(ISyntaxNode child) {
        ISyntaxNode root = null;

        if(child.position() < this.position && (associateType.score & 1) > 0){
            if(leftChild == null){
                this.leftChild = child;
                return true;
            }
            root = this.leftChild.associate(child);
            if(root != null){
                this.leftChild = root;
                return true;
            }
        }

        if((associateType.score & 2) > 0){
            if(rightChild == null){
                this.rightChild = child;
                return true;
            }
            root = this.rightChild.associate(child);
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
    public ISyntaxNode build(int position, IBuildContext context) {
        OpenSyntaxNode node = new OpenSyntaxNode(this.executor, this.word, this.priority, this.associateType);
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
