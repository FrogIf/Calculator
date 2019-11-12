package frog.calculator;

import frog.calculator.build.IExpressionBuilder;
import frog.calculator.build.command.ICommand;
import frog.calculator.build.command.ICommandDetector;
import frog.calculator.build.command.ICommandHolder;
import frog.calculator.build.resolve.IResolver;
import frog.calculator.build.resolve.IResolverResult;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.exception.BuildException;
import frog.calculator.exception.CalculatorError;
import frog.calculator.exception.ExpressionFormatException;
import frog.calculator.exec.space.Coordinate;
import frog.calculator.exec.space.IRange;
import frog.calculator.exec.space.ISpace;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionHolder;
import frog.calculator.math.BaseNumber;
import frog.calculator.util.collection.ITraveller;
import frog.calculator.util.collection.Queue;

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

    private IExpression build(String expression, ICalculatorSession session) throws BuildException {
        IExpressionBuilder builder = this.calculatorManager.createExpressionBuilder(session);
        try {
            this.build(expression, builder);
            builder.finishBuild();
            return builder.getRoot();
        } catch (BuildException e) {
            builder.buildFail();
            throw e;
        }
    }

    private void build(String expression, IExpressionBuilder builder) throws BuildException {
        char[] chars = expression.toCharArray();

        for (int i = 0; i < chars.length; ) {
            // 命令解析
            i += this.commandDetect(chars, i, builder);
            if(i >= chars.length){ break; }

            // 表达式解析
            IResolverResult result = this.resolve(chars, i, builder);
            if (result == null) {
                throw new ExpressionFormatException(expression, "undefined symbol : " + chars[i] + " at " + i);
            }

            // 构建
            if (builder.append(result.getExpression()) == null) {
                throw new ExpressionFormatException(expression, "expression format is not right at " + i);
            }

            int offset = result.offset();
            if (offset < 1) {
                throw new CalculatorError("system error : length of '" + result.getExpression().symbol() + "' is " + offset + ".");
            }
            i += offset;
        }
    }

    /**
     * 命令探查
     * @param chars 表达式串
     * @param startIndex 探查起始位置
     * @param builder 表达式构建器
     * @return 解析偏移量
     */
    private int commandDetect(char[] chars, int startIndex, IExpressionBuilder builder){
        ICommand command;
        int commandOffset = 0;
        int offset;
        do{
            command = detector.detect(chars, startIndex);
            if(command == null){ break; }
            offset = command.init(builder);
            builder.pushCommand(command);
            commandOffset += offset;
            startIndex += offset;
        }while (offset > 0 && startIndex < chars.length);

        return commandOffset;
    }

    private IResolverResult resolve(char[] chars, int startIndex, IExpressionBuilder builder) throws BuildException {
        // 解析前置命令
        ITraveller<ICommand> commands = builder.commandTraveller();
        boolean canOver = true; // 标记是否可以结束, 出栈顺序必须是从栈顶到栈底, 所以如果一个命令不能出栈, 那么它下面的也不能出栈
        Queue<ICommand> queue = new Queue<>();
        while(commands.hasNext()){
            ICommand c = commands.next();
            c.beforeResolve(chars, startIndex, builder);
            if(canOver && c.over(chars, startIndex, builder)){
                queue.enqueue(c);
            }else{
                canOver = false;
            }
        }
        while(!queue.isEmpty()){
            builder.popCommand(queue.dequeue());
        }

        // 尝试使用session解析器
        IResolverResult result = null;
        if(startIndex < chars.length){
            result = builder.getSession().resolveVariable(chars, startIndex);
            if(result == null){
                // 使用可执行解析器进行解析
                result = this.innerResolver.resolve(chars, startIndex);
            }else{
                IResolverResult tryResult = this.innerResolver.resolve(chars, startIndex);
                if(tryResult != null && tryResult.offset() > result.offset()){
                    result = tryResult;
                }
            }
        }

        // 解析后置命令
        commands = builder.commandTraveller();
        queue.clear();
        canOver = true;
        while(commands.hasNext()){
            ICommand c = commands.next();
            result = c.afterResolve(result, builder);
            if(canOver && c.over(chars, startIndex, builder)){
                queue.enqueue(c);
            }else{
                canOver = false;
            }
        }
        while(!queue.isEmpty()){
            builder.popCommand(queue.dequeue());
        }

        return result;
    }

    /**
     * 执行计算
     * @param expression 待计算的表达式
     * @param session    会话
     * @return 解析结果
     */
    public String calculate(String expression, ICalculatorSession session) throws BuildException {
        IExpression expTree = build(expression, session); // 构造解析树

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

}
