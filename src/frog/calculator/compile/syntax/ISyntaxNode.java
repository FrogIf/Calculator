package frog.calculator.compile.syntax;

import frog.calculator.util.collection.IList;
import frog.calculator.value.IValue;

public interface ISyntaxNode {

    /**
     * 字面量
     * @return
     */
    String literal();

    /**
     * 优先级
     * @return
     */
    int priority();

    /**
     * syntax node在整个表达式中的位置
     * @return
     */
    int position();

    /**
     * node是否处于Open状态
     * @return
     */
    boolean isOpen();

    /**
     * 结合
     * @param input 参与结合的另一个syntax node
     * @return 结合后的root, 如果结合失败, 将返回null
     */
    ISyntaxNode associate(ISyntaxNode input);

    /**
     * 向当前节点插入子节点
     * @param child 子节点
     * @return true - 插入成功, false - 插入失败
     */
    boolean branchOff(ISyntaxNode child);

    /**
     * 获取所有左下级子节点
     * @return 子节点集合
     */
    IList<ISyntaxNode> children();

    /**
     * 执行
     * @return
     */
    IValue execute();

}