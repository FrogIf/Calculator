package frog.calculator.build;

import frog.calculator.ICalculatorManager;
import frog.calculator.build.command.ICommand;
import frog.calculator.build.command.ICommandDetector;
import frog.calculator.build.pipe.IBuildPipe;
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
import frog.calculator.util.collection.ITraveller;
import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.Queue;
import frog.calculator.util.collection.Stack;

public class DefaultExpressionBuilder implements IExpressionBuilder {

    // 表达式树初始节点, 当有其它表达式节点参与构建时, 会自动使用该节点替换初始节点
    private static final IExpression INIT_ROOT = new StartExpression();

    // 内置运算符解析器
    private final IResolver innerResolver;

    // 命令探查器
    private final ICommandDetector detector;

    // 计算器管理器
    private final ICalculatorManager manager;

    // builder绑定的session会话
    private final ICalculatorSession session;

    // 局部变量栈, 仅用于构建单个表达式, 一旦表达式构建完成, 该栈将无效
    private Stack<IRegister<IExpression>> localRegisterStack;

    // 构建命令栈, 同局部变量栈, 仅用于单个表达式的构建, 一旦构建完成, 该栈失效
    private Stack<ICommand> commandStack;   // 会话命令栈

    // 构建结束监听, 构建完成调用, 调用之后销毁
    private Queue<IBuildFinishListener> buildOverListenerQueue;

    private IExpression root;

    private IBuildPipe buildRegion;

    private int order = 0;

    public DefaultExpressionBuilder(ICalculatorSession session, ICalculatorManager manager, IResolver innerResolver, ICommandDetector commandDetector) {
        this.session = session;
        this.manager = manager;
        this.innerResolver = innerResolver;
        this.detector = commandDetector;
    }

    private void init(){
        this.buildRegion = null;
        this.order = 0;
        this.root = INIT_ROOT;
        this.localRegisterStack = new Stack<>();
        this.commandStack = new Stack<>();
        this.buildOverListenerQueue = new Queue<>();
    }

    @Override
    public IExpression getRoot() {
        return this.root;
    }

    @Override
    public void addBuildFinishListener(IBuildFinishListener listener) {
        buildOverListenerQueue.enqueue(listener);
    }

    @Override
    public void setBuildPipe(IBuildPipe region) {
        this.buildRegion = region;
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

    private IResolverResult resolveVariable(char[] expChars, int startIndex) {
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
        if((expression != null) && (result == null || result.offset() <= expression.symbol().length())){
            result = this.manager.createResolverResult(expression.clone());
        }
        return result;
    }

    @Override
    public IExpression build(String expression) throws BuildException {
        this.init();    // 初始化builder

        char[] chars = expression.toCharArray();

        for (int i = 0; i < chars.length; ) {
            // 命令解析
            i += this.commandDetect(chars, i);
            if(i >= chars.length){ break; }

            // 表达式解析
            IResolverResult result = this.resolve(chars, i);
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
        while(!buildOverListenerQueue.isEmpty()){
            this.root = this.buildOverListenerQueue.dequeue().buildFinishCallBack(this);
        }
    }

    private IExpression append(IExpression expression) throws DuplicateSymbolException {
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
        while(commands.hasNext()){
            ICommand c = commands.next();
            c.beforeResolve(chars, startIndex, this);
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

        if(result != null){
            boolean canOver = true; // 标记是否可以结束, 出栈顺序必须是从栈顶到栈底, 所以如果一个命令不能出栈, 那么它下面的也不能出栈
            Queue<ICommand> queue = new Queue<>();
            String symbol = result.getExpression().symbol();
            while(commands.hasNext()){
                ICommand c = commands.next();
                result = c.afterResolve(result, this);
                if(canOver && c.over(symbol, this)){
                    queue.enqueue(c);
                }else{
                    canOver = false;
                }
            }
            while(!queue.isEmpty()){
                this.popCommand(queue.dequeue());
            }
        }else{
            throw new ExpressionFormatException(String.valueOf(chars), "undefined symbol : " + chars[startIndex] + " at " + startIndex);
        }

        return result;
    }

    /**
     * 表达式初始节点(头部哨兵)</br>
     * 多线程安全
     */
    private static class StartExpression extends AbstractExpression {

        private StartExpression() {
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
