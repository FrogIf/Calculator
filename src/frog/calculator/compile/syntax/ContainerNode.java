package frog.calculator.compile.syntax;

import frog.calculator.compile.semantic.exec.IExecutor;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.LinkedList;
import frog.calculator.util.collection.UnmodifiableList;

public class ContainerNode extends AbstractSyntaxNode implements ISyntaxNodeGenerator {

    private final LinkedList<ISyntaxNode> children;

    private boolean rightOpen = true;

    private final String end;

    public ContainerNode(String start, String end, IExecutor executor) {
        super(start, -1, executor);
        this.children = new LinkedList<>();
        this.end = end;
    }

	@Override
    public boolean isLeftOpen() {
        return false;
    }

    @Override
    public boolean isRightOpen() {
        return this.rightOpen;
    }

    @Override
    public boolean branchOff(ISyntaxNode child, IAssembler assembler) {
        if(this.rightOpen){
            if(end.equals(child.word())){
                this.rightOpen = false;
                return true;
            }
            ISyntaxNode last = children.last();
            if(last == null){
                children.add(child);
            }else{
                // 局部重新构建
                ISyntaxNode c = assembler.associate(last, child);
                if(c != null){
                    children.postRemove();
                    children.add(c);
                }else{
                    children.add(child);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public IList<ISyntaxNode> children() {
        return new UnmodifiableList<>(children);
    }

    @Override
    public ISyntaxNode generate(int position) {
        ContainerNode node = new ContainerNode(word, end, executor);
        node.position = position;
        return node;
    }
    
}
