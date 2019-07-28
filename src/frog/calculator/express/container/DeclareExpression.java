package frog.calculator.express.container;

import frog.calculator.express.IExpression;
import frog.calculator.operator.IOperator;

public class DeclareExpression extends ContainerExpression {
    /**
     * 容器表达式
     *
     * @param openSymbol  容器起始位置
     * @param operator    容器运算器
     * @param closeSymbol 容器终止位置
     */
    public DeclareExpression(String openSymbol, IOperator operator, String closeSymbol) {
        super(openSymbol, operator, closeSymbol);
    }

    @Override
    public IExpression interpret() {
        return super.interpret();
    }
}
