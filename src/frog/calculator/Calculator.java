package frog.calculator;

import frog.calculator.connect.ICalculatorSession;
import frog.calculator.express.DefaultExpressionContext;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.math.INumber;
import frog.calculator.register.IRegister;
import frog.calculator.register.TreeRegister;
import frog.calculator.resolver.IResolver;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.IResolverResultFactory;
import frog.calculator.resolver.ResolverResultType;
import frog.calculator.resolver.resolve.*;
import frog.calculator.resolver.resolve.factory.CustomFunctionExpressionFactory;
import frog.calculator.resolver.resolve.factory.ISymbolExpressionFactory;
import frog.calculator.resolver.resolve.factory.NumberExpressionFactory;
import frog.calculator.resolver.resolve.factory.VariableExpressionFactory;
import frog.calculator.resolver.util.CommonSymbolParse;
import frog.calculator.space.Coordinate;
import frog.calculator.space.ISpace;
import frog.calculator.util.collection.Stack;

public class Calculator {

    // configure
    private ICalculatorConfigure calculatorConfigure;

    // resolve result factory, used to pack resolver's result
    private IResolverResultFactory resolverResultFactory;

    // resolve the string which can't run directly.
    private IResolver runnableResolver;

    // if find the declare symbol, current resolve switch to this.
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

    // =============================================== init start =============================================

    // create framework inner defined symbol resolver
    private IResolver createRunnableResolver() {
        IExpressionHolder holder = this.calculatorConfigure.getExpressionHolder();
        // value resolver
        IResolver numberResolver = new NumberResolver(new NumberExpressionFactory(holder.getNumberOperator()), resolverResultFactory);

        // plus and minus resolver
        // plus and minus can represent (positive and negative) or (add and sub)
        // this resolver can operate like python
        IResolver addSubResolver = new PMResolver(resolverResultFactory,
                this.calculatorConfigure.getExpressionHolder().getPlus(),
                this.calculatorConfigure.getExpressionHolder().getMinus());

        // symbol resolver, can parse symbol which was supported by framework.
        IResolver symbolResolver = new SymbolResolver(resolverResultFactory,
                createRegister(holder.getBuiltInExpression()));

        // to check the next symbol is or not the declare symbol, if it is declare current resolver will switch to declare resolver.
        TreeRegister declareStart = new TreeRegister();
        IExpression declareBeginExpression = holder.getDeclareBegin();
        declareStart.insert(declareBeginExpression);
        IResolver declareStartListenResolver = new SymbolResolver(resolverResultFactory, declareStart, ResolverResultType.DECLARE_BEGIN);

        // parse execute order : value -> plus and minus -> symbol -> declare check.
        ChainResolver chainResolver = new ChainResolver(resolverResultFactory);

        chainResolver.addResolver(numberResolver);
        chainResolver.addResolver(addSubResolver);
        chainResolver.addResolver(symbolResolver);
        chainResolver.addResolver(declareStartListenResolver);

        return chainResolver;
    }

    // create declare symbol resolver
    private IResolver createDeclareResolver() {
        TreeRegister struct = new TreeRegister();
        IExpressionHolder expressionHolder = this.calculatorConfigure.getExpressionHolder();

        String closeSymbol = expressionHolder.getFunArgEnd().symbol(); // )
        String assignSymbol = expressionHolder.getAssign().symbol();    // =
        String splitSymbol = expressionHolder.getSeparator().symbol();  // ,
        String openSymbol = expressionHolder.getFunArgStart().symbol();   // (

        // declare part's structure symbol. the symbol will be recognize to custom symbol which in front of those structure symbol.
        struct.insert(expressionHolder.getSeparator());
        struct.insert(expressionHolder.getFunArgEnd());
        IResolver structResolver = new SymbolResolver(resolverResultFactory, struct);

        /*
         * check declare end.
         * for example :
         *      @a = 1; the '=' is the declare end symbol.
         *      @func1(x, y)-> x + y; the '->' is the declare end symbol.
         */
        TreeRegister declareEnd = new TreeRegister();
        IExpression declareEndExpression = expressionHolder.getAssign();
        IExpression delegate = expressionHolder.getDelegate();
        declareEnd.insert(declareEndExpression);
        declareEnd.insert(delegate);
        IResolver declareEndListenResolver = new SymbolResolver(resolverResultFactory, declareEnd, ResolverResultType.DECLARE_END);

        ISymbolExpressionFactory variableExpressionFactory = new VariableExpressionFactory();
        // variable resolver, cooperate with structure symbol resolver. in fact, this is a truncate resolver.
        IResolver variableResolver = new TruncateResolver(resolverResultFactory, new TruncateResolver.TruncateSymbol[]{
                new TruncateResolver.TruncateSymbol(closeSymbol, variableExpressionFactory),
                new TruncateResolver.TruncateSymbol(assignSymbol, variableExpressionFactory),
                new TruncateResolver.TruncateSymbol(splitSymbol, variableExpressionFactory),
                new TruncateResolver.TruncateSymbol(openSymbol, new CustomFunctionExpressionFactory(closeSymbol, splitSymbol), true)
        });


        ChainResolver chainResolver = new ChainResolver(resolverResultFactory);

        // parse execute order : struct resolver -> declare end -> variable.
        chainResolver.addResolver(structResolver);
        chainResolver.addResolver(declareEndListenResolver);
        chainResolver.addResolver(variableResolver);

        return chainResolver;
    }

    private IRegister createRegister(IExpression[] expressions) {
        TreeRegister register = new TreeRegister();
        for (IExpression exp : expressions) {
            register.insert(exp);
        }
        return register;
    }

    // =============================================== init end =============================================


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
        root.setOrder(order++);

        for (int i = rootResult.getEndIndex() + 1; i < chars.length; i++) {
            IResolverResult result = this.resolve(chars, i, localRegisterStack, resolverHolder, session);

            if (result == null) {
                throw new IllegalArgumentException("undefined symbol at " + i);
            }

            IExpression curExp = result.getExpression();
            curExp.setOrder(order++);

            root = root.assembleTree(curExp);

            if (root == null) {
                throw new IllegalStateException("tree root lost.");
            }

            i = result.getEndIndex();
        }

        root.setExpressionContext(context);

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
                    registerStack.top().insert(expression);  // 向局部变量表中添加变量
                }
            }
        }else{
            if (!registerStack.isEmpty()) {   // 应用局部变量表进行解析(先使用局部解析器, 在使用会话解析器 -- 就近原则)
                result = CommonSymbolParse.parseExpression(chars, startIndex, registerStack.top(), this.resolverResultFactory);
            }
            if(result.getExpression() == null){
                IExpression sessionVariable = session.getSessionVariable(chars, startIndex);
                result = CommonSymbolParse.generateResult(sessionVariable, startIndex, this.resolverResultFactory);
            }
            if (result.getExpression() == null) {
                throw new IllegalArgumentException("undefine symbol : " + chars[startIndex]);
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

        ISpace<INumber> result = expTree.interpret(); // 执行计算

        INumber value = result.get(new Coordinate(0));

        return value.toString();    // 计算结果
    }

    /**
     * 解析器持有者, 用来记录每一次构造过程中解析器的切换, 有点类似状态模式
     */
    private static class ResolverHolder {
        private IResolver resolver;
    }

    // ===============================================解析执行end=========================================

}
