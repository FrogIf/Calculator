package frog.calculator;

import frog.calculator.express.IExpression;
import frog.calculator.express.result.AResultExpression;
import frog.calculator.register.IRegister;
import frog.calculator.resolver.IResolver;
import frog.calculator.resolver.IResolverBuilder;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.build.DefaultResolverBuilder;
import frog.calculator.resolver.build.IBuilderPrototypeHolder;

/**
 * 计算器
 */
public class Calculator {

    private IResolver resolver;

    private IRegister register;

    public Calculator(IBuilderPrototypeHolder prototypeHolder) {
        IResolverBuilder builder = new DefaultResolverBuilder(prototypeHolder);
        this.register = builder.getRegister();
        this.resolver = builder.getResolver();
        resolver.setRegister(register);
    }

    public Calculator(IResolverBuilder builder) {
        this.register = builder.getRegister();
        this.resolver = builder.getResolver();
        resolver.setRegister(register);
    }

    /**
     * 构造解析树
     * @param expression
     * @return
     */
    private IExpression build(String expression){
        char[] chars = expression.toCharArray();

        IResolverResult rootResult = resolver.resolve(chars, 0);
        IExpression root = rootResult.getExpression();

        for(int i = rootResult.getEndIndex() + 1; i < chars.length; i++){
            IResolverResult result = resolver.resolve(chars, i);
            IExpression curExp = result.getExpression();

            root = root.assembleTree(curExp);

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

        AResultExpression result = expTree.interpret(); // 执行计算

        return result.resultValue();    // 计算结果
    }

    /**
     * 自定义函数
     * @param funDef
     * @param expression
     */
    public synchronized void defineFunction(String funDef, String expression){
        // TODO 自定义函数
    }
}
