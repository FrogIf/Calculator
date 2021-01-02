package frog.calculator.express;

import frog.calculator.ISymbol;
import frog.calculator.build.IBuildContext;
import frog.calculator.execute.space.ISpace;
import frog.calculator.express.support.IExpressionContext;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.util.collection.IList;

public interface IExpression extends ISymbol {

    /**
     * 最小构建因子
     */
    int MIN_BUILD_FACTOR = -1;

    /**
     * 创建树的分支<br />
     * 该方法会尝试以调用者本身作为根节点, 将传入节点作为子节点进行创建, 成功为true, 失败为false<br/>
     * 一般地, 只有当一个表达式是尝试进入一棵表达式树的节点时, 会被外部调用该方法, 否则, 均是类内部调用
     * @param childExpression 子表达式
     * @return true : 创建成功, false : 创建失败
     */
    boolean createBranch(IExpression childExpression);

    /**
     * 将调用者节点与传入节点组装为一棵树, 返回树的根节点
     * @param expression 待组装节点
     * @return 返回组装树的根节点
     */
    IExpression assembleTree(IExpression expression);

    /**
     * 是否是叶子节点
     * @return true 是, false 否
     */
    boolean isLeaf();

    /**
     * 构建因子, 根据构建因子的不同, 内部会判断那个节点是子节点, 哪个是父节点
     * @return
     */
    int buildFactor();

    /**
     * 复制表达式及其子表达式
     * @return
     */
    IExpression newInstance();

    /**
     * 获取表达式节点在整个表达式中的位置
     * @return
     */
    int order();

    /**
     * 解释执行表达式
     * @return
     */
    ISpace<BaseNumber> interpret();

    IList<IExpression> children();

    IExpressionContext getContext();

    void buildInit(int order, IExpressionContext context, IBuildContext buildContext);
}
