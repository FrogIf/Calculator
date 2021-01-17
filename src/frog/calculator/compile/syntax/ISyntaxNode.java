package frog.calculator.compile.syntax;

import frog.calculator.compile.IBuildContext;
import frog.calculator.compile.IWord;
import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.util.collection.IList;
import frog.calculator.value.IValue;

public interface ISyntaxNode extends IWord{

    /**
     * 语法节点符号
     * @return
     */
    String word();

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
     * 向当前节点插入子节点
     * @param child 子节点
     * @return true - 插入成功, false - 插入失败
     */
    boolean branchOff(ISyntaxNode child, IBuildContext context);

    /**
     * 获取所有左下级子节点
     * @return 子节点集合
     */
    IList<ISyntaxNode> children();

    /**
     * 执行
     * @return
     */
    IValue execute(IExecuteContext context);

}