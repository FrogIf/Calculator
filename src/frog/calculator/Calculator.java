package frog.calculator;

import frog.calculator.express.IExpression;
import frog.calculator.express.result.ResultExpression;
import frog.calculator.resolve.IResolveResult;
import frog.calculator.resolve.IResolver;

/**
 * 计算器
 */
public class Calculator {

    private ICalculatorConfigure configure;

    private IResolver resolver;

    public Calculator() {
        this.configure = new DefaultCalculatorConfigure();
        resolver = this.configure.getResolverConfigure().getResolver();
    }

    public Calculator(ICalculatorConfigure configure) {
        this.configure = configure;
    }

    /**
     * 构造解析树
     * @param expression
     * @return
     */
    private IExpression build(String expression){
        char[] chars = expression.toCharArray();

        IResolveResult rootResult = resolver.resolve(chars, 0);
        IExpression root = rootResult.getExpression();

        for(int i = rootResult.getEndIndex() + 1; i < chars.length; i++){
            IResolveResult result = resolver.resolve(chars, i);

            root = root.assembleTree(result.getExpression());

            if(root == null){
                throw new IllegalStateException("tree root lost.");
            }

            i = result.getEndIndex();
        }

        return root;
    }

    public String calculate(String expression){
        expression = expression.replaceAll(" ", "");

        IExpression expTree = build(expression); // 去空格

//        foreachExpressionTree(tree, exp -> new MonitorExpressionWrapper(exp));

        ResultExpression result = expTree.interpret(); // 执行计算

        return result.resultValue();    // 计算结果
    }

//    private IExpression foreachExpressionTree(IExpression expression, ExpressionNodeHandler expressionNodeHandler){
//        IExpression[] branches = expression.branches();
//
//        IExpression transExp = expressionNodeHandler.handleExpression(expression);
//
//        if(branches != null && branches.length != 0){
//            for(IExpression exp : branches){
//                IExpression tExp = expressionNodeHandler.handleExpression(exp);
//                foreachExpressionTree(tExp, expressionNodeHandler);
//            }
//        }
//
//        return transExp;
//    }
}
