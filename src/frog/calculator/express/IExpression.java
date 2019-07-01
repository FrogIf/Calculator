package frog.calculator.express;

import frog.calculator.express.result.ResultExpression;
import frog.calculator.operate.IOperator;

public interface IExpression extends Cloneable{

    ResultExpression interpret();

    /**
     * 获取当前表达式的优先级
     * @return
     */
    int priority();

    boolean isLeaf();

    /**
     * 以当前表达式作为根, 创建下级分支
     * @param expression
     * @return 当前表达式的下级分支, 如果创建失败, 返回null
     */
    boolean createBranch(IExpression expression);

    /**
     * 将表达式对象本身与传入的表达式组装成树, 如果不能组装在一起, 则返回null
     * @param expression
     * @return
     */
    IExpression assembleTree(IExpression expression);

    /**
     * 设置表达式运算器
     * @param operator
     */
    void setOperator(IOperator operator);

    IExpression clone();
}