package frog.calculator.compile.syntax;

public interface IOpenStatusChangeListener {

    /**
     * 当node的open状态发生改变时回调
     * @param selfNode 发生改变的节点本身
     * @return 用于替换发生改变的节点的新的节点
     */
    ISyntaxNode onChange(ISyntaxNode selfNode);
    
}
