package frog.calculator;

import frog.calculator.dimpl.DoubleCalculatorConfigure;
import frog.calculator.express.IExpression;
import frog.calculator.express.result.AResultExpression;
import frog.calculator.register.IRegister;
import frog.calculator.resolve.IResolveResult;
import frog.calculator.resolve.IResolver;

/**
 * 计算器
 */
public class Calculator {

    private ICalculatorConfigure configure;

    private IResolver resolver;

    private IRegister register;

    public Calculator() {
        this(new DoubleCalculatorConfigure());
    }

    public Calculator(ICalculatorConfigure configure) {
        this.configure = configure;
        resolver = this.configure.getResolver();
        register = this.configure.getRegister();
        resolver.setRegister(register);
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
    public void defineFunction(String funDef, String expression){
        // TODO 自定义函数
    }
}
