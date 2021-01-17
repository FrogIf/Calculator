package frog.calculator.compile.syntax;

import frog.calculator.compile.IBuildContext;
import frog.calculator.compile.ISyntaxTreeBuilder;
import frog.calculator.compile.semantic.IExecutor;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.LinkedList;

public class DynamicOpenSyntaxNode extends AbstractSyntaxNode implements ISyntaxNodeBuilder {

    protected final IOpenStatusManager manager;

    private boolean leftOpen = true;

    private boolean rightOpen = true;

    private final LinkedList<ISyntaxNode> leftChildren;

    private final LinkedList<ISyntaxNode> rightChildren;

    public DynamicOpenSyntaxNode(IExecutor executor, String word, int priority, IOpenStatusManager manager) {
        super(executor, word, priority);
        this.manager = manager;
        this.leftChildren = manager.initLeft ? new LinkedList<>() : null;
        this.rightChildren = manager.initRight ? new LinkedList<>() : null;
    }

	@Override
    public boolean isOpen() {
        return leftOpen && rightOpen;
    }

    @Override
    public boolean branchOff(ISyntaxNode child, IBuildContext context) {
        if(leftOpen && child.position() < this.position()){
            addChildren(this.leftChildren, child, context);
        }else{
            this.leftOpen = false;
            if(rightOpen){
                addChildren(this.rightChildren, child, context);
                this.rightOpen = this.manager.isOpen(child);
            }else{
                return false;
            }
        }
        return true;
    }

    private void addChildren(LinkedList<ISyntaxNode> children, ISyntaxNode child, IBuildContext context){
        ISyntaxNode last = children.last();
        if(last == null){
            children.add(child);
        }else{
            ISyntaxTreeBuilder builder = context.getBuilder();
            // 局部重新构建
            ISyntaxNode c = builder.associate(last, child, context);
            if(c != null){
                children.postRemove();
                children.add(c);
            }else{
                children.add(child);
            }
        }
    }

    @Override
    public IList<ISyntaxNode> children() {
        ArrayList<ISyntaxNode> children = new ArrayList<>(this.leftChildren.size() + this.rightChildren.size());
        children.addAll(this.leftChildren);
        children.addAll(this.rightChildren);
        return children;
    }

    @Override
    public ISyntaxNode build(int position, IBuildContext context) {
        DynamicOpenSyntaxNode node = new DynamicOpenSyntaxNode(executor, word, priority, manager);
        node.position = position;
        return node;
    }

    public abstract class IOpenStatusManager {

        private final boolean initLeft;

        private final boolean initRight;

        /**
         * @param left 左侧结构初始open状态
         * @param right 右侧结构初始open状态
         */
        protected IOpenStatusManager(boolean left, boolean right){
            this.initLeft = left;
            this.initRight = right;
        }

        abstract boolean isOpen(ISyntaxNode node);
    }
    
}
