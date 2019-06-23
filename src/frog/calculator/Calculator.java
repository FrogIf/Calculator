package frog.calculator;

import frog.calculator.express.IExpression;
import frog.calculator.express.MonitorExpressionWrapper;
import frog.calculator.express.result.ResultExpression;
import frog.calculator.operate.IOperator;
import frog.calculator.operate.IOperatorPool;
import frog.calculator.resolve.IResolveResult;
import frog.calculator.resolve.IResolver;

/**
 * 计算器
 */
public class Calculator {

    private ICalculatorConfigure configure;

    private IResolver resolver;

    private IOperatorPool operatorPool;

    public Calculator() {
        this.configure = new DefaultCalculatorConfigure();
        resolver = this.configure.getResolverConfigure().getResolver();
        operatorPool = this.configure.getOperatorConfigure().getIOperatorPool();
    }

    public Calculator(ICalculatorConfigure configure) {
        this.configure = configure;
    }

    private IExpression explain(String expression){
        char[] chars = expression.toCharArray();

        IResolveResult rootResult = resolver.resolve(chars, 0);
        IExpression root = this.handleResolveResult(rootResult);

        for(int i = rootResult.getEndIndex() + 1; i < chars.length; i++){
            IResolveResult result = resolver.resolve(chars, i);

            IExpression exp = this.handleResolveResult(result);

            root = root.assembleTree(exp);

            if(root == null){
                throw new IllegalStateException("tree root lost.");
            }

            i = result.getEndIndex();
        }

        return root;
    }

    private IExpression handleResolveResult(IResolveResult result){
        if(result.getExpression() == null) {
            throw new IllegalArgumentException("can't recognize expression.");
        }

        IExpression exp = result.getExpression();

        IOperator operator = operatorPool.getOperator(result.getSymbol());

        if(operator == null){
            throw new IllegalStateException("can't find operator for it.");
        }else{
            exp.setOperator(operator);
        }

        return result.getExpression();
    }

    public String calculate(String expression){
        IExpression tree = explain(expression.replaceAll(" ", ""));

        foreachExpressionTree(tree, exp -> new MonitorExpressionWrapper(exp));

        ResultExpression result = tree.interpret();

        return result.resultValue();
    }

    private IExpression foreachExpressionTree(IExpression expression, ExpressionNodeHandler expressionNodeHandler){
        IExpression[] branches = expression.branches();

        IExpression transExp = expressionNodeHandler.handleExpression(expression);

        if(branches != null && branches.length != 0){
            for(IExpression exp : branches){
                IExpression tExp = expressionNodeHandler.handleExpression(exp);
                foreachExpressionTree(tExp, expressionNodeHandler);
            }
        }

        return transExp;
    }
}
