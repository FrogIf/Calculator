package frog.calculator.express.container;

import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.operator.IOperator;
import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.LinkedList;

/**
 * 内置函数表达式
 */
public class FunctionExpression extends ContainerExpression{

    String splitSymbol;

    private LinkedList<Argument> args = new LinkedList<>();

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
    public boolean buildContent(IExpression childExpression) {
        if(splitSymbol.equals(childExpression.symbol())){
            args.add(new Argument());
        }else{
            if(args.isEmpty()){
                args.add(new Argument());
            }
            args.last().addToCurrentNode(childExpression);
        }
        return true;
    }

    public IExpression call(IExpression[] expressions){
        // TODO 实现调用
        return this.interpret();
    }

    @Override
    public IExpression interpret() {
        if(!args.isEmpty()){
            IExpression[] expressions = new IExpression[args.size()];
            Iterator<Argument> iterator = args.iterator();
            for(int i = 0; i < args.size(); i++){
                Argument arg = iterator.next();
                expressions[i] = arg.argExp;
            }
            return this.operator.operate(this.symbol(), context, expressions);
        }else{
            return this.operator.operate(this.symbol(), context, null);
        }
    }

    @Override
    public void setExpressionContext(IExpressionContext context) {
        this.context = context;
        if(!this.args.isEmpty()){
            Iterator<Argument> iterator = this.args.iterator();
            while (iterator.hasNext()){
                Argument next = iterator.next();
                if(next.argExp != null){
                    next.argExp.setExpressionContext(context);
                }
            }
        }
    }

    @Override
    public IExpression clone() {
        FunctionExpression fun = (FunctionExpression) super.clone();
        fun.args = new LinkedList<>();
        return fun;
    }

    private static class Argument {

        private IExpression argExp;

        private void addToCurrentNode(IExpression childExpression) {
            if(argExp == null){
                argExp = childExpression;
            }else{
                argExp = argExp.assembleTree(childExpression);
                if(argExp == null){
                    throw new IllegalArgumentException("expression can't assemble.");
                }
            }
        }
    }
}
