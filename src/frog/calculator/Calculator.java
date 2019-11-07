package frog.calculator;

import frog.calculator.command.ICommand;
import frog.calculator.command.ICommandDetector;
import frog.calculator.command.ICommandHolder;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.exception.BuildException;
import frog.calculator.exception.CalculatorError;
import frog.calculator.exception.ExpressionFormatException;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.express.IExpressionHolder;
import frog.calculator.math.BaseNumber;
import frog.calculator.resolver.IResolver;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.space.Coordinate;
import frog.calculator.space.IRange;
import frog.calculator.space.ISpace;
import frog.calculator.util.collection.ITraveller;

public class Calculator {

    // 计算管理器
    private ICalculatorManager calculatorManager;

    // resolve inner symbol
    private IResolver innerResolver;

    // 命令探测器
    private ICommandDetector detector;

    public Calculator(ICalculatorConfigure calculatorConfigure) {
        if (calculatorConfigure == null) {
            throw new IllegalArgumentException("configure is null.");
        }
        ICalculatorComponentFactory componentFactory = calculatorConfigure.getComponentFactory();
        this.calculatorManager = calculatorConfigure.getComponentFactory().createCalculatorManager(calculatorConfigure);
        IExpressionHolder expressionHolder = componentFactory.createExpressionHolder();
        this.innerResolver = componentFactory.createResolver(expressionHolder, this.calculatorManager);
        ICommandHolder commandHolder = componentFactory.createCommandHolder(this.calculatorManager, calculatorConfigure);
        this.detector = componentFactory.createCommandDetector(commandHolder);
    }

    // ===============================================解析执行start=========================================

    private IExpression build(String expression, ICalculatorSession session, IExpressionContext context) throws BuildException {
        char[] chars = expression.toCharArray();
        int order = 0;

        int i = commandDetect(chars, 0, session);
        IResolverResult rootResult = this.resolve(chars, i, session);

        if (rootResult == null) {
            this.failure(session);
            throw new ExpressionFormatException(expression, "undefined symbol : " + chars[i]);
        }

        IExpression root = rootResult.getExpression();
        root.setOrder(order++);
        context.setRoot(root);

        for (i = i + rootResult.offset(); i < chars.length; ) {
            i += commandDetect(chars, i, session);
            if(i >= chars.length){ break; }
            IResolverResult result = this.resolve(chars, i, session);

            if (result == null) {
                this.failure(session);
                throw new ExpressionFormatException(expression, "undefined symbol : " + chars[i] + " at " + i);
            }

            IExpression curExp = result.getExpression();
            curExp.setOrder(order++);
            curExp.setExpressionContext(context);

            root = context.getRoot().assembleTree(curExp);

            if (root == null) {
                this.failure(session);
                throw new ExpressionFormatException(expression, "expression format is not right at " + i);
            }

            context.setRoot(root);

            int offset = result.offset();
            if (offset < 1) {
                throw new CalculatorError("system error : length of '" + result.getExpression().symbol() + "' is " + offset + ".");
            }
            i += offset;
        }

        context.finishBuild();

        return context.getRoot();
    }

    private int commandDetect(char[] chars, int startIndex, ICalculatorSession session){
        ICommand command;
        int commandOffset = 0;
        int offset;
        do{
            command = detector.detect(chars, startIndex);
            if(command == null){ break; }
            offset = command.init(session);
            session.pushCommand(command);
            commandOffset += offset;
            startIndex += offset;
        }while (offset > 0 && startIndex < chars.length);

        return commandOffset;
    }

    private void failure(ICalculatorSession session){
        ITraveller<ICommand> commands = session.commandTraveller();
        while(commands.hasNext()){
            ICommand c = commands.next();
            c.buildFailedCallback(session);
        }
    }

    private IResolverResult resolve(char[] chars, int startIndex, ICalculatorSession session) {
        // 解析前置命令
        ITraveller<ICommand> commands = session.commandTraveller();
        while(commands.hasNext()){
            ICommand c = commands.next();
            c.beforeResolve(chars, startIndex, session);
            if(c.over(chars, startIndex, session)){
                session.popCommand(c);
            }
        }

        // 尝试使用session解析器
        IResolverResult result = null;
        if(startIndex < chars.length){
            result = session.resolveVariable(chars, startIndex);
            if(result == null){
                // 使用可执行解析器进行解析
                result = this.innerResolver.resolve(chars, startIndex);
            }
        }

        // 解析后置命令
        commands = session.commandTraveller();
        while(commands.hasNext()){
            ICommand c = commands.next();
            result = c.afterResolve(result, session);
            if(c.over(chars, startIndex, session)){
                session.popCommand(c);
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
    public String calculate(String expression, ICalculatorSession session) throws BuildException {
        IExpressionContext context = this.calculatorManager.createExpressionContext(session);

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

    public ICalculatorSession getSession(){
        return this.calculatorManager.createCalculatorSession();
    }

    // ===============================================解析执行end=========================================

}
