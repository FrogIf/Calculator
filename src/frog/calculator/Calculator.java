package frog.calculator;

import frog.calculator.command.CommandDetector;
import frog.calculator.command.ICommand;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.express.DefaultExpressionContext;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.math.BaseNumber;
import frog.calculator.register.IRegister;
import frog.calculator.register.SymbolRegister;
import frog.calculator.resolver.IResolver;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.resolve.ChainResolver;
import frog.calculator.resolver.resolve.NumberResolver;
import frog.calculator.resolver.resolve.PMResolver;
import frog.calculator.resolver.resolve.SymbolResolver;
import frog.calculator.resolver.resolve.factory.NumberExpressionFactory;
import frog.calculator.space.Coordinate;
import frog.calculator.space.IRange;
import frog.calculator.space.ISpace;
import frog.calculator.util.collection.ITraveller;

public class Calculator {

    // configure
    private final ICalculatorConfigure calculatorConfigure;

    // 计算管理器
    private final ICalculatorManager calculatorManager;

    // resolve inner symbol
    private IResolver innerResolver;

    private CommandDetector detector;

    public Calculator(ICalculatorConfigure calculatorConfigure) {
        if (calculatorConfigure == null) {
            throw new IllegalArgumentException("configure is null.");
        }
        this.calculatorConfigure = calculatorConfigure;
        this.calculatorManager = new DefaultCalculatorManager(calculatorConfigure);
        ICommandHolder commandHolder = new DefaultCommandHolder(this.calculatorManager, this.calculatorConfigure);  // TODO 需优化

        this.innerResolver = initInnerResolver();
        this.detector = new CommandDetector(this.createCommandRegister(commandHolder.getCommands()));
    }

    // =============================================== init start =============================================

    // create framework inner defined symbol resolver
    private IResolver initInnerResolver() {
        IExpressionHolder holder = this.calculatorConfigure.getExpressionHolder();
        // value resolver
        IResolver numberResolver = new NumberResolver(new NumberExpressionFactory(), this.calculatorManager);

        // plus and minus resolver
        // plus and minus can represent (positive and negative) or (add and sub)
        // this resolver can transform like python
        IResolver addSubResolver = new PMResolver(calculatorManager,
                this.calculatorConfigure.getExpressionHolder().getPlus(),
                this.calculatorConfigure.getExpressionHolder().getMinus());

        // symbol resolver, can parse symbol which was supported by framework.
        IResolver symbolResolver = new SymbolResolver(calculatorManager,
                createRegister(holder.getBuiltInExpression()));

        // parse execute order : value -> plus and minus -> symbol
        ChainResolver chainResolver = new ChainResolver();
        chainResolver.addResolver(numberResolver);
        chainResolver.addResolver(addSubResolver);
        chainResolver.addResolver(symbolResolver);

        return chainResolver;
    }

    private IRegister<IExpression> createRegister(IExpression[] expressions) {
        SymbolRegister<IExpression> register = new SymbolRegister<>();
        for (IExpression exp : expressions) {
            register.insert(exp);
        }
        return register;
    }

    private IRegister<ICommand> createCommandRegister(ICommand[] commands){
        SymbolRegister<ICommand> register = new SymbolRegister<>();
        for (ICommand command : commands) {
            register.insert(command);
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
            throw new IllegalArgumentException("undefined symbol : " + chars[0]);
        }

        IExpression root = rootResult.getExpression();
        root.setOrder(order++);

        for (int i = rootResult.offset(); i < chars.length; ) {
            IResolverResult result = this.resolve(chars, i, session);

            if (result == null) {
                throw new IllegalArgumentException("undefined symbol : " + chars[i]);
            }

            IExpression curExp = result.getExpression();
            curExp.setOrder(order++);

            root = root.assembleTree(curExp);

            if (root == null) {
                throw new IllegalStateException("expression format is not right at " + i);
            }

            int offset = result.offset();
            if(offset < 1){
                throw new IllegalStateException("system error : " + result.getExpression().symbol());
            }
            i += offset;
        }

        root.setExpressionContext(context);

        return root;
    }

    private IResolverResult resolve(char[] chars, int startIndex, ICalculatorSession session) {
        // 使用命令解析器进行解析
        ICommand command;
        int offset;
        do{
            command = detector.detect(chars, startIndex);
            if(command == null){ break; }
            offset = command.init(session);
            session.pushCommand(command);
            startIndex += offset;
        }while (offset > 0);

        // 解析前置命令
        ITraveller<ICommand> commands = session.commandTraveller();
        while(commands.hasNext()){
            ICommand c = commands.next();
            c.beforeResolve(chars, startIndex, session);
            if(c.over(chars, startIndex)){
                session.popCommand();
            }
        }

        // 尝试使用session解析器
        IResolverResult result = session.resolveVariable(chars, startIndex);
        if(result == null){
            // 使用可执行解析器进行解析
            result = this.innerResolver.resolve(chars, startIndex);
        }

        // 解析后置命令
        commands = session.commandTraveller();
        while(commands.hasNext()){
            ICommand c = commands.next();
            result = c.afterResolve(result, session);
            if(c.over(chars, startIndex)){
                session.popCommand();
            }
        }

        return result;
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

    public ICalculatorManager getCalculatorManager(){
        return this.calculatorManager;
    }

    // ===============================================解析执行end=========================================

}
