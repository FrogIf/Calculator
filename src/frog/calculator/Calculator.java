package frog.calculator;

import frog.calculator.connect.ICalculatorSession;
import frog.calculator.express.DefaultExpressionContext;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.register.IRegister;
import frog.calculator.register.TreeRegister;
import frog.calculator.resolver.IResolver;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.IResolverResultFactory;
import frog.calculator.resolver.ResolverResultType;
import frog.calculator.resolver.resolve.*;
import frog.calculator.resolver.resolve.factory.CustomFunctionExpressionFactory;
import frog.calculator.resolver.resolve.factory.DefaultNumberExpressionFactory;
import frog.calculator.resolver.resolve.factory.ICustomSymbolExpressionFactory;
import frog.calculator.resolver.resolve.factory.VariableExpressionFactory;
import frog.calculator.resolver.util.CommonSymbolParse;
import frog.calculator.util.LinkedList;
import frog.calculator.util.Stack;

/**
 * 计算器, 计算的解析, 执行, 调度
 */
public class Calculator {

    // 配置bean
    private ICalculatorConfigure calculatorConfigure;

    // 解析结果工厂
    private IResolverResultFactory resolverResultFactory;

    // 运行解析器
    private IResolver runnableResolver;

    // 声明解析器
    private IResolver declareResolver;

    public Calculator(ICalculatorConfigure calculatorConfigure) {
        if (calculatorConfigure == null) {
            throw new IllegalArgumentException("configure is null.");
        }
        this.calculatorConfigure = calculatorConfigure;
        this.resolverResultFactory = calculatorConfigure.getResolverResultFactory();

        this.runnableResolver = createRunnableResolver();
        this.declareResolver = createDeclareResolver();
    }

    // ===============================================初始化start=============================================

    private IResolver createRunnableResolver() {
        // 数字解析器
        IResolver numberResolver = new NumberResolver(new DefaultNumberExpressionFactory(), resolverResultFactory);

        // 加, 减解析器; 加减又可以当做正负使用, 所以需要单独使用解析器进行解析
        IResolver addSubResolver = new PMResolver(resolverResultFactory,
                this.calculatorConfigure.getExpressionHolder().getPlus(),
                this.calculatorConfigure.getExpressionHolder().getMinus());

        // 符号解析器, 用于解析系统内部支持的运算符
        IResolver symbolResolver = new SymbolResolver(resolverResultFactory,
                createRegister(this.calculatorConfigure.getExpressionHolder().getBuiltInExpression()));

        // 监听声明开始
        TreeRegister declareStart = new TreeRegister();
        IExpression declareBeginExpression = this.calculatorConfigure.getExpressionHolder().getDeclareBegin();
        declareStart.registe(declareBeginExpression.symbol(), declareBeginExpression);
        IResolver declareStartListenResolver = new SymbolResolver(resolverResultFactory, declareStart, ResolverResultType.DECLARE_BEGIN);

        // 解析器执行顺序: 数字解析 -> 加减解析 -> 符号解析 -> 声明开始监听解析器
        ChainResolver chainResolver = new ChainResolver(resolverResultFactory);

        chainResolver.addResolver(numberResolver);
        chainResolver.addResolver(addSubResolver);
        chainResolver.addResolver(symbolResolver);
        chainResolver.addResolver(declareStartListenResolver);

        return chainResolver;
    }

    private IResolver createDeclareResolver() {
        // 声明结构解析器
        TreeRegister struct = new TreeRegister();
        IExpressionHolder expressionHolder = this.calculatorConfigure.getExpressionHolder();
        struct.registe(expressionHolder.getSeparator().symbol(), expressionHolder.getSeparator());
        struct.registe(expressionHolder.getContainerClose().symbol(), expressionHolder.getContainerClose());
        IResolver structResolver = new SymbolResolver(resolverResultFactory, struct);

        // 监听声明结束
        TreeRegister declareEnd = new TreeRegister();

        IExpression declareEndExpression = this.calculatorConfigure.getExpressionHolder().getAssign();  // "=" 标记声明结束
        declareEnd.registe(declareEndExpression.symbol(), declareEndExpression);

        IExpression delegate = this.calculatorConfigure.getExpressionHolder().getDelegate();
        declareEnd.registe(delegate.symbol(), delegate);

        IResolver declareEndListenResolver = new SymbolResolver(resolverResultFactory, declareEnd, ResolverResultType.DECLARE_END);

        ICustomSymbolExpressionFactory customSymbolExpressionFactory = new VariableExpressionFactory();

        // 变量解析器(截断解析器)
        IResolver variableResolver = new TruncateResolver(resolverResultFactory, new TruncateResolver.TruncateSymbol[]{
                new TruncateResolver.TruncateSymbol(",", customSymbolExpressionFactory),
                new TruncateResolver.TruncateSymbol(")", customSymbolExpressionFactory),
                new TruncateResolver.TruncateSymbol("=", customSymbolExpressionFactory),
                new TruncateResolver.TruncateSymbol("(", new CustomFunctionExpressionFactory(")", ",", "->"), true)
        });


        ChainResolver chainResolver = new ChainResolver(resolverResultFactory);

        // 解析器执行顺序: 声明结束监听解析器 -> 变量解析器
        chainResolver.addResolver(structResolver);
        chainResolver.addResolver(declareEndListenResolver);
        chainResolver.addResolver(variableResolver);

        return chainResolver;
    }

    private IRegister createRegister(IExpression[] expressions) {
        TreeRegister register = new TreeRegister();
        for (IExpression exp : expressions) {
            register.registe(exp.symbol(), exp, exp.getOperator());
        }
        return register;
    }

    // ===============================================初始化end=============================================


    // ===============================================解析执行start=========================================

    private IExpression build(String expression, ICalculatorSession session, IExpressionContext context) {
        ResolverHolder resolverHolder = new ResolverHolder();
        resolverHolder.resolver = this.runnableResolver;

        char[] chars = expression.toCharArray();
        int order = 0;

        Stack<IRegister> localRegisterStack = new Stack<>(); // 局部注册器栈, 用于存储局部变量

        IResolverResult rootResult = this.resolve(chars, 0, localRegisterStack, resolverHolder, session);

        if (rootResult == null) {
            throw new IllegalArgumentException("undefined symbol at " + 0);
        }

        IExpression root = rootResult.getExpression();
        root.setExpressionContext(context);
        root.setOrder(order++);

        for (int i = rootResult.getEndIndex() + 1; i < chars.length; i++) {
            IResolverResult result = this.resolve(chars, i, localRegisterStack, resolverHolder, session);

            if (result == null) {
                throw new IllegalArgumentException("undefined symbol at " + i);
            }

            IExpression curExp = result.getExpression();
            curExp.setExpressionContext(context);
            curExp.setOrder(order++);

            root = root.assembleTree(curExp);

            if (root == null) {
                throw new IllegalStateException("tree root lost.");
            }

            i = result.getEndIndex();
        }



        return root;
    }

    private IResolverResult resolve(char[] chars, int startIndex, Stack<IRegister> registerStack,
                                    ResolverHolder resolverHolder, ICalculatorSession session) {
        IResolver resolver = resolverHolder.resolver;
        IResolverResult result = resolver.resolve(chars, startIndex);

        if(result.getExpression() != null){
            if (result.getType() != null) {
                if (result.getType() == ResolverResultType.DECLARE_BEGIN) {
                    registerStack.push(new TreeRegister()); // 创建一个新的局部变量表
                    resolverHolder.resolver = this.declareResolver;   // 切换至声明解析器
                } else if (result.getType() == ResolverResultType.DECLARE_END) {
//                    registerStack.pop();    // 销毁顶端局部变量表
                    // TODO 临时取消销毁顶部局部变量表
                    resolverHolder.resolver = this.runnableResolver;  // 切换至执行解析器
                } else if (result.getType() == ResolverResultType.DECLARE) {   // 声明
                    IExpression expression = result.getExpression();
                    registerStack.getTop().registe(expression.symbol(), expression, expression.getOperator());  // 向局部变量表中添加变量
                }
            }
        }else{
            if (registerStack.isNotEmpty()) {   // 应用局部变量表进行解析(先使用局部解析器, 在使用会话解析器 -- 就近原则)
                result = CommonSymbolParse.parseExpression(chars, startIndex, registerStack.getTop(), this.resolverResultFactory);
            }
            if(result.getExpression() == null){
                result = CommonSymbolParse.parseExpression(chars, startIndex, session.getUserRegister(), this.resolverResultFactory);
            }
            if (result.getExpression() == null) {
                throw new IllegalArgumentException("undefine symbol at " + startIndex);
            }
        }

        return result.getExpression() == null ? null : result;
    }

    /**
     * 执行计算
     *
     * @param expression 待计算的表达式
     * @param session    会话
     * @return 解析结果
     */
    public String calculate(String expression, ICalculatorSession session) {
        IExpressionContext context = new DefaultExpressionContext(session);

        IExpression expTree = build(expression, session, context); // 构造解析树

        IExpression result = expTree.interpret(); // 执行计算

        // 获取顶层表达式的局部变量表, 就是会话变量
        LinkedList<IExpression> variables = context.getLocalVariables();
        LinkedList<IExpression>.Iterator iterator = variables.getIterator();
        IRegister userRegister = session.getUserRegister();
        while(iterator.hasNext()){
            IExpression next = iterator.next();
            userRegister.replace(next.symbol(), next, next.getOperator());
        }

        return result.symbol();    // 计算结果
    }

    /**
     * 解析器持有者, 用来记录每一次构造过程中解析器的切换, 有点类似状态模式
     */
    private static class ResolverHolder {
        private IResolver resolver;
    }

    // ===============================================解析执行end=========================================

}
