package frog.calculator;

import frog.calculator.connect.ICalculatorSession;
import frog.calculator.express.DefaultExpressionContext;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.math.BaseNumber;
import frog.calculator.register.IRegister;
import frog.calculator.register.SymbolRegister;
import frog.calculator.resolver.IResolver;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.IResolverResultFactory;
import frog.calculator.resolver.resolve.ChainResolver;
import frog.calculator.resolver.resolve.NumberResolver;
import frog.calculator.resolver.resolve.PMResolver;
import frog.calculator.resolver.resolve.SymbolResolver;
import frog.calculator.resolver.resolve.factory.NumberExpressionFactory;
import frog.calculator.space.Coordinate;
import frog.calculator.space.IRange;
import frog.calculator.space.ISpace;

public class Calculator {

    // configure
    private ICalculatorConfigure calculatorConfigure;

    // resolve result factory, used to pack resolver's result
    private IResolverResultFactory resolverResultFactory;

    // resolve inner symbol
    private IResolver innerResolver;

    public Calculator(ICalculatorConfigure calculatorConfigure) {
        if (calculatorConfigure == null) {
            throw new IllegalArgumentException("configure is null.");
        }
        this.calculatorConfigure = calculatorConfigure;
        this.resolverResultFactory = calculatorConfigure.getResolverResultFactory();

        this.innerResolver = createInnerResolver();
    }

    // =============================================== init start =============================================

    // create framework inner defined symbol resolver
    private IResolver createInnerResolver() {
        IExpressionHolder holder = this.calculatorConfigure.getExpressionHolder();
        // value resolver
        IResolver numberResolver = new NumberResolver(new NumberExpressionFactory(), resolverResultFactory);

        // plus and minus resolver
        // plus and minus can represent (positive and negative) or (add and sub)
        // this resolver can transform like python
        IResolver addSubResolver = new PMResolver(resolverResultFactory,
                this.calculatorConfigure.getExpressionHolder().getPlus(),
                this.calculatorConfigure.getExpressionHolder().getMinus());

        // symbol resolver, can parse symbol which was supported by framework.
        IResolver symbolResolver = new SymbolResolver(resolverResultFactory,
                createRegister(holder.getBuiltInExpression()));

        // parse execute order : value -> plus and minus -> symbol
        ChainResolver chainResolver = new ChainResolver(resolverResultFactory);
        chainResolver.addResolver(numberResolver);
        chainResolver.addResolver(addSubResolver);
        chainResolver.addResolver(symbolResolver);

        return chainResolver;
    }

    private IRegister createRegister(IExpression[] expressions) {
        SymbolRegister register = new SymbolRegister();
        for (IExpression exp : expressions) {
            register.insert(exp);
        }
        return register;
    }

    // =============================================== init end =============================================


    // ===============================================解析执行start=========================================

    private IExpression build(String expression, ICalculatorSession session, IExpressionContext context) {
        char[] chars = expression.toCharArray();
        int order = 0;

        IResolverResult rootResult = this.resolve(chars, 0, session);

        if (rootResult == null) {
            throw new IllegalArgumentException("undefined symbol at " + 0);
        }

        IExpression root = rootResult.getExpression();
        root.setOrder(order++);

        for (int i = rootResult.getEndIndex() + 1; i < chars.length; i++) {
            IResolverResult result = this.resolve(chars, i, session);

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

    private IResolverResult resolve(char[] chars, int startIndex, ICalculatorSession session) {
        IResolverResult result = this.innerResolver.resolve(chars, startIndex);

        if (result.getExpression() == null){    // 使用截断解析器进行声明式解析, '=', ',', '(', ')'
            // 1. 从session获取
            // 2. 使用截断解析器进行解析
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

        ISpace<BaseNumber> result = expTree.interpret(); // 执行计算

        IRange range = result.getRange();
        int[] widths = range.maxWidths();
        if(widths.length == 1 && widths[0] == 1){
            return result.get(new Coordinate(0)).toString();
        }else{
            StringBuilder sb = new StringBuilder("[");
            int[] coordinateArr = new int[range.dimension()];
            for(int i = coordinateArr.length - 1; i >= 0; i--){
                int w = widths[i];
                for(int j = 0; j < w; j++){
                    coordinateArr[i] = j;
                    BaseNumber number = result.get(new Coordinate(coordinateArr));
                    if(j > 0){
                        sb.append(',');
                    }
                    sb.append(number == null ? "null" : number.toString());
                }
                if(i > 0){ sb.append(';'); }
            }
            sb.append("]");
            return sb.toString();
        }
    }

    // ===============================================解析执行end=========================================

}
