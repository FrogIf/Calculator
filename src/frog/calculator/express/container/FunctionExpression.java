package frog.calculator.express.container;

import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.operator.IOperator;

/**
 * 内置函数表达式
 */
public class FunctionExpression extends ContainerExpression{

    private String splitSymbol;

    protected ArgumentNode args = new ArgumentNode();

    /**
     * 创建一个内置函数表达式
     * 一个内置函数结构由以下几部分组成:
     *  容器起始 [参数1 分割符 参数2 分隔符 ... ] 容器终止
     * @param openSymbol 容器起始位置
     * @param operator  函数实际算法
     * @param closeSymbol 容器终止位置
     * @param splitSymbol 参数分割符
     */
    public FunctionExpression(String openSymbol, IOperator operator, String closeSymbol, String splitSymbol){
        super(openSymbol, operator, closeSymbol);
        this.splitSymbol = splitSymbol;
    }

    @Override
    public boolean createBranch(IExpression childExpression) {
        if(isClose){
            return false;
        }else{
            if(this.closeSymbol.equals(childExpression.symbol())){
                throw new IllegalStateException("expression error.");
            }

            if(this.suspendExpression == null && this.order() > childExpression.order()){
                this.suspendExpression = childExpression;
            }else{
                if(splitSymbol.equals(childExpression.symbol())){
                    this.args.setTailClose();
                    return true;
                }
                return this.args.addExpression(childExpression);
            }
        }
        return true;
    }

    @Override
    public IExpression interpret() {
        return this.operator.operate(this.symbol(), context, args.getArguments());
    }

    @Override
    public void setExpressionContext(IExpressionContext context) {
        IExpression[] expressions = this.args.getArguments();
        for(IExpression expression : expressions){
            expression.setExpressionContext(context);
        }
    }

    @Override
    public IExpression clone() {
        FunctionExpression clone = (FunctionExpression) super.clone();
        clone.args = this.args == null ? null : this.args.copy();
        return clone;
    }
}
