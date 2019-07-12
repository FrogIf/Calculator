package frog.calculator.express;

import frog.calculator.express.result.AResultExpression;
import frog.calculator.operater.IOperator;

public interface IExpression extends Cloneable {

    /**
     * 解释执行表达式
     * @return
     */
    AResultExpression interpret();

    /**
     * 获取当前表达式的优先级
     * @return
     */
    int priority();

    /**
     * 以当前表达式作为根, 创建下级分支
     * @param expression
     * @return 当前表达式的下级分支, 如果创建失败, 返回false
     */
    boolean createBranch(IExpression expression);

    /**
     * 将表达式对象本身与传入的表达式组装成树, 如果不能组装在一起, 则返回null
     * @param expression
     * @return
     */
    IExpression assembleTree(IExpression expression);

    /**
     * 复制表达式对象
     * 在解析器中, 每解析出一个表达式, 会应用原型模式调用这个方法, 复制一个表达式对象
     * @return
     */
    IExpression clone();

    /**
     * 返回在输入的算式中, 该表达式所代表的那部分
     * @return
     */
    String symbol();

    /**
     * 获取运算器
     * @return
     */
    IOperator getOperator();

    /**
     * 获取表达式类型, 只是提供表达式对象之间在构建解析树时使用
     * @return
     */
    ExpressionType type();
}