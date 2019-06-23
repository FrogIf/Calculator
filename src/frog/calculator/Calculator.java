package frog.calculator;

import frog.calculator.expression.IExpression;
import frog.calculator.expression.MonitorExpressionWrapper;
import frog.calculator.expression.end.NumberExpression;
import frog.calculator.expression.mid.AddExpression;
import frog.calculator.resolver.DefaultResolverConfigure;
import frog.calculator.resolver.IResolveResult;
import frog.calculator.resolver.IResolver;
import frog.calculator.resolver.IResolverConfigure;

/**
 * 计算器
 */
public class Calculator {

    private IResolverConfigure configure;

    public Calculator() {
        this.configure = new DefaultResolverConfigure();
    }

    public Calculator(IResolverConfigure configure) {
        this.configure = configure;
    }

    private IExpression explain(String expression){
        char[] chars = expression.toCharArray();

        AddExpression initExp = new AddExpression();
        initExp.setLeft(new NumberExpression("0"));
        IExpression root = initExp;

        IResolver resolver = configure.getResolver();

        for(int i = 0; i < chars.length; i++){
            IResolveResult result = resolver.resolve(chars, i);

            if(result.getExpression() == null) {
                throw new IllegalArgumentException("can't recognize expression.");
            }

            root = root.assembleTree(result.getExpression());

            if(root == null){
                throw new IllegalStateException("tree root lost.");
            }

            i = result.getEndIndex();
        }

        return root;
    }

    public double calculate(String expression){
        IExpression tree = explain(expression.replaceAll(" ", ""));

        foreachExpressionTree(tree, exp -> new MonitorExpressionWrapper(exp));

        return tree.interpret();
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
