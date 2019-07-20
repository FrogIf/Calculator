package frog.calculator;

import frog.calculator.connect.ICalculatorSession;
import frog.calculator.express.ArgumentExpression;
import frog.calculator.express.IExpression;
import frog.calculator.express.container.CustomFunctionExpression;
import frog.calculator.express.endpoint.VariableExpression;
import frog.calculator.register.IRegister;
import frog.calculator.register.TreeRegister;
import frog.calculator.resolver.IResolver;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.IResolverResultFactory;
import frog.calculator.resolver.resolve.*;
import frog.calculator.resolver.resolve.factory.DefaultNumberExpressionFactory;

/**
 * 计算器, 计算的解析, 执行, 调度
 */
public class Calculator {

    private ICalculatorConfigure calculatorConfigure;

    private IResolver innerResolver;    // 内置解析器

    private IResolver declareResolver;  // 变量, 函数声明解析器

    public Calculator(ICalculatorConfigure calculatorConfigure) {
        if(calculatorConfigure == null){
            throw new IllegalArgumentException("configure is null.");
        }
        this.calculatorConfigure = calculatorConfigure;
        this.innerResolver = createResolver();
    }

    /**
     * 创建解析器
     * @return
     */
    private IResolver createResolver(){
        ChainResolver resolverChain = new ChainResolver();

        IResolverResultFactory resolverResultFactory = this.calculatorConfigure.getResolverResultFactory();

        // 数字解析器
        IResolver numberResolver = new NumberResolver(new DefaultNumberExpressionFactory(), resolverResultFactory);

        // 加, 减解析器; 加减又可以当做正负使用, 所以比较需要单独使用解析器进行解析
        IResolver addSubResolver = new AddSubResolver(resolverResultFactory,
                this.calculatorConfigure.getExpressionHolder().getAddExpression(),
                this.calculatorConfigure.getExpressionHolder().getSubExpression());

        // 符号解析器, 用于解析系统内部支持的运算符
        IResolver symbolResolver = new SymbolResolver(resolverResultFactory,
                createInnerExpressionRegister());

        IResolver lambdaDeclareResolver = new DeclareResolver(resolverResultFactory,
                this.calculatorConfigure.getExpressionHolder().getDeclareExpression());

        // 解析器执行顺序: 数字解析 -> 加减解析 -> 符号解析 -> 声明解析器
        resolverChain.addResolver(numberResolver);
        resolverChain.addResolver(addSubResolver);
        resolverChain.addResolver(symbolResolver);
        resolverChain.addResolver(lambdaDeclareResolver);

        return resolverChain;
    }

    /**
     * 注册符号
     * @return
     */
    private IRegister createInnerExpressionRegister(){
        IExpression[] innerExpression = this.calculatorConfigure.getExpressionHolder().getInnerExpression();
        TreeRegister register = new TreeRegister();
        for(IExpression exp : innerExpression){
            register.registe(exp.symbol(), exp, exp.getOperator());
        }
        return register;
    }

    /**
     * 构造解析树
     * @param expression
     * @return
     */
    private IExpression build(String expression, IResolver outerResolver){
        char[] chars = expression.toCharArray();
        int order = 0;

        IResolverResult rootResult = this.innerResolver.resolve(chars, 0);
        if(rootResult == null && outerResolver != null){
            rootResult = outerResolver.resolve(chars, 0);
        }

        IExpression root = rootResult.getExpression();

        root.setOrder(order++);

        for(int i = rootResult.getEndIndex() + 1; i < chars.length; i++){
            IResolverResult result = this.innerResolver.resolve(chars, i);

            if(result == null && outerResolver != null){
                result = outerResolver.resolve(chars, i);
            }

            IExpression curExp = result.getExpression();

            curExp.setOrder(order++);

            root = root.assembleTree(curExp);

            if(root == null){
                throw new IllegalStateException("tree root lost.");
            }

            i = result.getEndIndex();
        }

        return root;
    }

    /**
     * 执行计算
     * @param expression 待计算的表达式
     * @param sessionResolver 会话解析器
     * @return
     */
    public String calculate(String expression, IResolver sessionResolver){
        expression = expression.replaceAll(" ", "");

        IExpression expTree = build(expression, sessionResolver); // 去空格

        IExpression result = expTree.interpret(); // 执行计算

        return result.symbol();    // 计算结果
    }

    /**
     * @param funOpen
     * @param args
     * @param funClose
     * @param body
     * @param session
     * @param splitor
     */
    public void defineFunction(String funOpen, String[] args, String funClose, String body, ICalculatorSession session, String splitor){
        IRegister outerRegister = session.getRegister();

        IExpression[] argumentExpressions = null;
        if(args != null && args.length > 0){
            argumentExpressions = new IExpression[args.length];
            for(int i = 0, len = args.length; i < len; i++){
                String arg = args[i];
                outerRegister.registe(arg, new VariableExpression(arg));
                argumentExpressions[i] = new ArgumentExpression(arg);
            }
        }

        // 解析函数体
        IExpression funBody = build(body, session.getSessionResolver());

        CustomFunctionExpression cFun = new CustomFunctionExpression(funOpen, funClose, splitor, funBody, argumentExpressions);

        outerRegister.registe(cFun.symbol(), cFun);
    }

}
