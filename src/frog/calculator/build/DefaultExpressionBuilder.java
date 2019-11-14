package frog.calculator.build;

import frog.calculator.ICalculatorManager;
import frog.calculator.build.command.ICommand;
import frog.calculator.build.command.ICommandDetector;
import frog.calculator.build.region.IBuildPipe;
import frog.calculator.build.register.IRegister;
import frog.calculator.build.register.SymbolRegister;
import frog.calculator.build.resolve.IResolver;
import frog.calculator.build.resolve.IResolverResult;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.exception.BuildException;
import frog.calculator.exception.CalculatorError;
import frog.calculator.exception.DuplicateSymbolException;
import frog.calculator.exception.ExpressionFormatException;
import frog.calculator.exec.space.AtomSpace;
import frog.calculator.exec.space.ISpace;
import frog.calculator.express.AbstractExpression;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.math.BaseNumber;
import frog.calculator.util.collection.*;

public class DefaultExpressionBuilder implements IExpressionBuilder {

    private static final IExpression initRoot = new StartExpression();

    // resolve inner symbol
    private IResolver innerResolver;

    // 命令探测器
    private ICommandDetector detector;

    private Stack<IRegister<IExpression>> localRegisterStack = new Stack<>(); // 变量栈, 栈底是session全局变量, 其余是局部变量

    private IList<IBuildFinishListener> buildOverListeners = new LinkedList<>();

    private IExpression root = initRoot;

    private IBuildPipe buildRegion;

    private int order = 0;

    private Stack<ICommand> commandStack = new Stack<>();   // 会话命令栈

    private ICalculatorSession session;

    private ICalculatorManager manager;

    public DefaultExpressionBuilder(ICalculatorSession session, ICalculatorManager manager, IResolver innerResolver, ICommandDetector commandDetector) {
        this.session = session;
        this.manager = manager;
        this.innerResolver = innerResolver;
        this.detector = commandDetector;
    }

    @Override
    public IExpression getRoot() {
        return this.root;
    }

    @Override
    public void addBuildFinishListener(IBuildFinishListener listener) {
        buildOverListeners.add(listener);
    }

    @Override
    public void setBuildPipe(IBuildPipe region) {
        this.buildRegion = region;
    }

    @Override
    public ICalculatorSession getSession() {
        return this.session;
    }

    @Override
    public void pushCommand(ICommand command) {
        this.commandStack.push(command);
    }

    @Override
    public void popCommand(ICommand command) {
        ICommand pop = this.commandStack.pop();
        if(pop != command){
            this.commandStack.push(pop);
            throw new IllegalStateException("assign command is not in stack top.");
        }
    }

    @Override
    public void addVariable(IExpression expression) throws DuplicateSymbolException {
        if(this.localRegisterStack.isEmpty()){
            this.session.addVariable(expression);
        }else{
            this.localRegisterStack.top().insert(expression);
        }
    }

    @Override
    public void createLocalVariableTable() {
        localRegisterStack.push(new SymbolRegister<>());
    }

    @Override
    public IRegister<IExpression> popLocalVariableTable() {
        if(this.localRegisterStack.isEmpty()){
            throw new IllegalStateException("no local variable region can be pop.");
        }else{
            return localRegisterStack.pop();
        }
    }

    @Override
    public IResolverResult resolveVariable(char[] expChars, int startIndex) {
        Iterator<IRegister<IExpression>> iterator = localRegisterStack.iterator();
        IExpression expression = null;
        while(iterator.hasNext()){
            IRegister<IExpression> reg = iterator.next();
            IExpression tExp = reg.retrieve(expChars, startIndex);
            if(tExp != null && (expression == null || tExp.symbol().length() > expression.symbol().length())){  // 贪心策略, 最长匹配原则
                expression = tExp;
            }
        }
        IResolverResult result = this.session.resolveVariable(expChars, startIndex);
        if((expression != null) && (result == null || result.offset() < expression.symbol().length())){
            result = this.manager.createResolverResult(expression.clone());
        }
        return result;
    }

    @Override
    public IExpression build(String expression) throws BuildException {
        char[] chars = expression.toCharArray();

        for (int i = 0; i < chars.length; ) {
            // 命令解析
            i += this.commandDetect(chars, i);
            if(i >= chars.length){ break; }

            // 表达式解析
            IResolverResult result = this.resolve(chars, i);
            if (result == null) {
                throw new ExpressionFormatException(expression, "undefined symbol : " + chars[i] + " at " + i);
            }

            // 构建
            if (this.append(result.getExpression()) == null) {
                throw new ExpressionFormatException(expression, "expression format is not right at " + i);
            }

            int offset = result.offset();
            if (offset < 1) {
                throw new CalculatorError("system error : length of '" + result.getExpression().symbol() + "' is " + offset + ".");
            }
            i += offset;
        }

        finishBuild();

        return this.root;
    }

    private void finishBuild() {
        if(!buildOverListeners.isEmpty()){
            Iterator<IBuildFinishListener> iterator = buildOverListeners.iterator();
            while(iterator.hasNext()){
                this.root = iterator.next().buildFinishCallBack(this);
            }
        }
    }

    private IExpression append(IExpression expression) {
        expression.buildInit(this.order++, null, this); // TODO Context
        if(buildRegion != null){
            if(expression.symbol().equals(buildRegion.symbol())){
                this.buildRegion.matchCallBack(this);
            }else{
                this.buildRegion = null;
            }
        }
        this.root = this.root.assembleTree(expression);
        return this.root;
    }


    /**
     * 命令探查
     * @param chars 表达式串
     * @param startIndex 探查起始位置
     * @return 解析偏移量
     */
    private int commandDetect(char[] chars, int startIndex){
        ICommand command;
        int commandOffset = 0;
        int offset;
        do{
            command = detector.detect(chars, startIndex);
            if(command == null){ break; }
            offset = command.init(this);
            this.pushCommand(command);
            commandOffset += offset;
            startIndex += offset;
        }while (offset > 0 && startIndex < chars.length);

        return commandOffset;
    }

    private IResolverResult resolve(char[] chars, int startIndex) throws BuildException {
        // 解析前置命令
        ITraveller<ICommand> commands = this.commandStack.iterator();
        boolean canOver = true; // 标记是否可以结束, 出栈顺序必须是从栈顶到栈底, 所以如果一个命令不能出栈, 那么它下面的也不能出栈
        Queue<ICommand> queue = new Queue<>();
        while(commands.hasNext()){
            ICommand c = commands.next();
            c.beforeResolve(chars, startIndex, this);
            if(canOver && c.over(chars, startIndex, this)){
                queue.enqueue(c);
            }else{
                canOver = false;
            }
        }
        while(!queue.isEmpty()){
            this.popCommand(queue.dequeue());
        }

        // 尝试使用session解析器
        IResolverResult result = null;
        if(startIndex < chars.length){
            result = resolveVariable(chars, startIndex);
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
        commands = this.commandStack.iterator();
        queue.clear();
        canOver = true;
        while(commands.hasNext()){
            ICommand c = commands.next();
            result = c.afterResolve(result, this);
            if(canOver && c.over(chars, startIndex, this)){
                queue.enqueue(c);
            }else{
                canOver = false;
            }
        }
        while(!queue.isEmpty()){
            this.popCommand(queue.dequeue());
        }

        return result;
    }

    /**
     * 表达式起点</br>
     * 多线程安全
     */
    private static class StartExpression extends AbstractExpression {

        public StartExpression() {
            super("", null);
        }

        @Override
        public boolean createBranch(IExpression childExpression) {
            return false;
        }

        @Override
        public IExpression assembleTree(IExpression expression) {
            return expression;
        }

        @Override
        public boolean isLeaf() {
            return false;
        }

        @Override
        public int buildFactor() {
            return 0;
        }

        @Override
        public boolean hasNextChild() {
            return false;
        }

        @Override
        public IExpression nextChild() {
            return null;
        }

        @Override
        public int order() {
            return -1;
        }

        @Override
        public void buildInit(int order, IExpressionContext context, IExpressionBuilder builder) {
            // do nothing
        }

        private ISpace<BaseNumber> value = new AtomSpace<>(BaseNumber.ZERO);

        @Override
        public ISpace<BaseNumber> interpret() {
            return this.value;
        }

        @Override
        public IExpression clone() {
            return this;
        }

    }

}