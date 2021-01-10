package frog.calculator.compile.syntax;

import frog.calculator.compile.semantic.IExecutor;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.LinkedList;

public class DynamicOpenSyntaxNode extends AbstractSyntaxNode implements ISyntaxNodeBuilder {

    protected final IOpenStatusManager manager;

    private boolean leftOpen = true;

    private boolean rightOpen = false;

    private final LinkedList<ISyntaxNode> leftChildren;

    private final LinkedList<ISyntaxNode> rightChildren;

    public DynamicOpenSyntaxNode(IExecutor executor, String literal, int priority, IOpenStatusManager manager) {
        super(executor, literal, priority);
        this.manager = manager;
        InitOpenStatus openStatus = new InitOpenStatus();
        this.manager.init(openStatus);
        this.leftChildren = openStatus.left ? new LinkedList<>() : null;
        this.rightChildren = openStatus.right ? new LinkedList<>() : null;
    }

	@Override
    public boolean isOpen() {
        return leftOpen && rightOpen;
    }

    @Override
    public boolean branchOff(ISyntaxNode child) {
        if(leftOpen){
            addChildren(this.leftChildren, child);
            this.leftOpen = this.manager.leftOpen(child);
        }else if(rightOpen){
            addChildren(this.rightChildren, child);
            this.rightOpen = this.manager.rightOpen(child);
        }else{
            return false;
        }
        return true;
    }

    private void addChildren(LinkedList<ISyntaxNode> children, ISyntaxNode child){
        ISyntaxNode last = children.last();
        if(last == null){
            children.add(child);
        }else{
            ISyntaxNode c = last.associate(child);
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
    public ISyntaxNode build(int position, ISyntaxTreeContext context) {
        DynamicOpenSyntaxNode node = new DynamicOpenSyntaxNode(executor, literal, priority, manager);
        node.context = context;
        node.position = position;
        return node;
    }

    @Override
    protected IOpenStatusChangeListener getOpenStatusChangeListener(){
        return this.manager;
    }

    public interface IOpenStatusManager extends IOpenStatusChangeListener {

        void init(InitOpenStatus openStatus);

        boolean leftOpen(ISyntaxNode node);

        boolean rightOpen(ISyntaxNode node);

    }

    public static class InitOpenStatus {
        boolean left = true;
        boolean right = true;
        public void setStatus(boolean left, boolean right){
            this.left = left;
            this.right = right;
        }
    }
    
}
