package sch.frog.calculator.compile.syntax;

import sch.frog.calculator.compile.IWord;
import sch.frog.calculator.compile.semantic.IExecuteContext;
import sch.frog.calculator.compile.semantic.result.IResult;
import sch.frog.calculator.util.collection.IList;

/**
 * 抽象语法树的节点
 */
public interface ISyntaxNode extends IWord {

    /**
     * syntax node在整个表达式中的位置
     */
    int position();

    /**
     * 优先级, 值越大, 优先级越高
     */
    int priority();

    /**
     * node左侧是否为可结合状态
     * @return true 是; false 否
     */
    boolean isLeftOpen();

    /**
     * node右侧是否是可结合状态
     * @return ture 是; false 否
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
     * 获取所有下级子节点
     * @return 子节点集合
     */
    IList<ISyntaxNode> children();

    /**
     * 执行
     * @return 执行结果
     */
    IResult execute(IExecuteContext context);

}