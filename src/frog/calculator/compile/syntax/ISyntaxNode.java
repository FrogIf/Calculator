package frog.calculator.compile.syntax;

import frog.calculator.compile.IWord;
import frog.calculator.compile.semantic.IExecuteContext;
import frog.calculator.compile.semantic.IResult;
import frog.calculator.util.collection.IList;

public interface ISyntaxNode extends IWord {

    /**
     * 语法节点符号
     * @return
     */
    String word();

    /**
     * syntax node在整个表达式中的位置
     * @return
     */
    int position();

    /**
     * 优先级
     * @return
     */
    int priority();

    /**
     * node左侧是否为可结合状态
     * @return true 是
     */
    boolean isLeftOpen();

    /**
     * node右侧是否是可结合状态
     * @return false 否
     */
    boolean isRightOpen();

    /**
     * 向当前节点插入子节点
     * @param child 子节点
     * @param assembler 组装两个节点在一起的组装器
     * @return true - 插入成功, false - 插入失败
     */
    boolean branchOff(ISyntaxNode child, IAssembler assembler);

    /**
     * 获取所有左下级子节点
     * @return 子节点集合
     */
    IList<ISyntaxNode> children();

    /**
     * 执行
     * @return
     */
    void execute(IExecuteContext context);

}