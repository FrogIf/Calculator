package frog.calculator.explain;

import frog.calculator.ICalculatorContext;
import frog.calculator.ICalculateListener;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.exception.BuildException;
import frog.calculator.exception.CalculatorError;
import frog.calculator.exception.DuplicateSymbolException;
import frog.calculator.exception.ExpressionFormatException;
import frog.calculator.execute.space.AtomSpace;
import frog.calculator.execute.space.ISpace;
import frog.calculator.explain.command.ICommand;
import frog.calculator.explain.register.IRegister;
import frog.calculator.explain.register.SymbolRegister;
import frog.calculator.explain.resolve.IResolverResult;
import frog.calculator.express.GhostExpression;
import frog.calculator.express.IExpression;
import frog.calculator.express.support.IExpressionContext;
import frog.calculator.express.AbstractExpression;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.util.StringUtils;
import frog.calculator.util.collection.*;

public class DefaultExpressionBuilder implements IExpressionBuilder {

    // 表达式树初始节点, 当有其它表达式节点参与构建时, 会自动使用该节点替换初始节点
    private static final IExpression INIT_ROOT = new StartExpression();

    // 计算器管理器
    private final IExplainManager manager;

    // builder绑定的session会话
    private final ICalculatorSession session;

    // 局部变量栈, 仅用于构建单个表达式, 一旦表达式构建完成, 该栈将无效
    private Stack<IRegister<IExpression>> localRegisterStack;

    // 临时记录session变量, 用于在表达式执行失败是, 删除session中的不完整变量
    private IList<IExpression> tempSessionVariableRecord = new LinkedList<>();

    // 构建命令栈, 同局部变量栈, 仅用于单个表达式的构建, 一旦构建完成, 该栈失效
    private Stack<ICommand> commandStack;   // 会话命令栈

    // 构建结束监听, 构建完成调用, 调用之后销毁
    private Queue<IBuildFinishListener> buildOverListenerQueue;

    // 计算监听器, 覆盖整个计算周期的监听器
    private ICalculateListener calculateListener = new CalculateListener();

    private IExpression root;

    private int order = 0;

    public DefaultExpressionBuilder(ICalculatorSession session, IExplainManager manager) {
        this.session = session;
        this.manager = manager;
    }

    private void init(){
        this.order = 0;
        this.root = INIT_ROOT;
        this.localRegisterStack = new Stack<>();
        this.commandStack = new Stack<>();
        this.buildOverListenerQueue = new Queue<>();
        this.tempSessionVariableRecord.clear();
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
    public void addVariable(IExpression expression) throws DuplicateSymbolException {
        if(this.localRegisterStack.isEmpty()){
            this.tempSessionVariableRecord.add(expression);
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

        IExpression sessionExp = this.session.resolveVariable(expChars, startIndex);    // 查找session中有没有更优的匹配变量
        if((expression == null) || (sessionExp != null && sessionExp.symbol().length() > expression.symbol().length())){
            expression = sessionExp;
        }
        return expression == null ? null : this.manager.assembleResolveResult(expression.newInstance());
    }

    @Override
    public IExpression build(char[] expression) throws BuildException {
        try{
            this.init();    // 初始化builder

            for (int i = 0; i < expression.length; ) {
                // 命令解析
                i += this.commandDetect(expression, i);
                if(i >= expression.length){ break; }

                // 表达式解析
                IResolverResult result = this.resolve(expression, i);
                // 构建
                if (this.append(result.getExpression()) == null) {
                    throw new ExpressionFormatException(StringUtils.concat(expression), "expression format is not right at " + i);
                }

                int offset = result.offset();
                if (offset < 1) {
                    throw new CalculatorError("system error : length of '" + result.getExpression().symbol() + "' is " + offset + ".");
                }
                i += offset;
            }

            finishBuild();

            return this.root;
        }catch (Exception e){
            this.failed();
            throw e;
        }
    }

    @Override
    public void viewCalculatorContext(ICalculatorContext context) {
        context.addCalculateListener(this.calculateListener);
    }

    private void failed(){
        if(!this.tempSessionVariableRecord.isEmpty()){
            Iterator<IExpression> iterator = this.tempSessionVariableRecord.iterator();
            while (iterator.hasNext()){
                this.session.removeVariable(iterator.next().symbol());
                iterator.remove();
            }
        }
    }

    private void finishBuild() {
        while(!buildOverListenerQueue.isEmpty()){
            this.root = this.buildOverListenerQueue.dequeue().buildFinishCallBack(this);
        }
    }

    private IExpression append(IExpression expression) {
        expression.buildInit(this.order++, null, this); // TODO Context
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
            command = this.manager.getCommandDetector().detect(chars, startIndex);
            if(command == null){ break; }
            offset = command.init(this);
            this.commandStack.push(command);
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

        // 解析
        IResolverResult result = null;
        if(startIndex < chars.length){
            result = resolveVariable(chars, startIndex);    // 使用内部解析器进行解析
            if(result == null){
                // 使用可执行解析器进行解析
                result = this.manager.getResolver().resolve(chars, startIndex); // 使用全局解析器
            }else{
                IResolverResult tryResult = this.manager.getResolver().resolve(chars, startIndex);  // 使用全局解析器
                if(tryResult != null && tryResult.offset() > result.offset()){
                    result = tryResult;
                }
            }
        }

        // 解析后命令处理
        commands = this.commandStack.iterator();

        if(result != null){
            boolean canOver = true; // 标记是否可以结束, 出栈顺序必须是从栈顶到栈底, 所以如果一个命令不能出栈, 那么它下面的也不能出栈
            Queue<ICommand> queue = new Queue<>();
            String symbol = result.getExpression().symbol();
            while(commands.hasNext()){
                ICommand c = commands.next();
                result = c.afterResolve(result, this);
                if(canOver && c.over(symbol, this)){
                    queue.enqueue(c);   // 压入待pop栈
                }else{
                    canOver = false;
                }
            }
            while(!queue.isEmpty()){
                // 栈中已结束的命令出栈
                ICommand pop = this.commandStack.pop();
                if(pop != queue.dequeue()){
                    this.commandStack.push(pop);
                    throw new IllegalStateException("assign command is not in stack top.");
                }
            }
        }else{
            throw new ExpressionFormatException(String.valueOf(chars), "undefined symbol : " + chars[startIndex] + " at " + startIndex);
        }

        return result;
    }

    private class CalculateListener implements ICalculateListener {

        @Override
        public void failed() {
            DefaultExpressionBuilder.this.failed();
        }

        @Override
        public void success() {
            // do nothing
        }
    }

    /**
     * 表达式初始节点(头部哨兵)<br/>
     * 多线程安全
     */
    private static class StartExpression extends GhostExpression {

        @Override
        public boolean createBranch(IExpression childExpression) {
            return false;
        }

        @Override
        public IExpression assembleTree(IExpression expression) {
            return expression;
        }

        @Override
        public int order() {
            return -1;
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
