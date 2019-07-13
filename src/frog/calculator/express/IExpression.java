package frog.calculator.express;

import frog.calculator.operater.IOperator;

public interface IExpression extends Cloneable {

    /**
     * 创建树的分支<br />
     * 该方法会尝试以调用者本身作为根节点, 将传入节点作为子节点进行创建, 成功为true, 失败为false
     * @param childExpression
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
     * 表达式字面值的字符串表示<br />
     * 约定: 返回值需与传入待解析字符串中完全吻合, 内部逻辑需要使用这一约定<br />
     * 如果是动态生成表达式, 如结果表达式, 该字面值也必须保证是解析器可以识别的
     * @return
     */
    String symbol();

    /**
     * 解释执行表达式
     * @return
     */
    IExpression interpret();

    /**
     * 是否是叶子节点
     * @return
     */
    boolean isLeaf();

    /**
     * 构建因子, 根据构建因子的不同, 内部会判断那个节点是子节点, 哪个是父节点
     * @return
     */
    int buildFactor();

    /**
     * 设置当前表达式上下文, 每一个表达式树有一个共同的表达式上下文
     * @param context
     */
    void setExpressionContext(IExpressionContext context);

    IOperator getOperator();

    /**
     * 复制表达式及其子表达式
     * @return
     */
    IExpression clone();
}
