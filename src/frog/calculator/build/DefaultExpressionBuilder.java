package frog.calculator.build;

import frog.calculator.build.command.ICommand;
import frog.calculator.build.region.IBuildRegion;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.exec.space.AtomSpace;
import frog.calculator.exec.space.ISpace;
import frog.calculator.express.AbstractExpression;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.math.BaseNumber;
import frog.calculator.util.collection.*;

public class DefaultExpressionBuilder implements IExpressionBuilder {

    private static final IExpression initRoot = new StartExpression();

    private IList<IBuildFinishListener> buildOverListeners = new LinkedList<>();

    private IExpression root = initRoot;

    private IBuildRegion buildRegion;

    private int order = 0;

    private Stack<ICommand> commandStack = new Stack<>();   // 会话命令栈

    private ICalculatorSession session;

    public DefaultExpressionBuilder(ICalculatorSession session) {
        this.session = session;
    }

    @Override
    public IExpression getRoot() {
        return root;
    }

    @Override
    public void finishBuild() {
        if(!buildOverListeners.isEmpty()){
            Iterator<IBuildFinishListener> iterator = buildOverListeners.iterator();
            while(iterator.hasNext()){
                this.root = iterator.next().buildFinishCallBack(this);
            }
        }
    }

    @Override
    public void buildFail() {
        ITraveller<ICommand> commands = this.commandTraveller();
        while(commands.hasNext()){
            ICommand c = commands.next();
            c.buildFailedCallback(this);
        }
    }

    @Override
    public void addBuildFinishListener(IBuildFinishListener listener) {
        buildOverListeners.add(listener);
    }

    @Override
    public IExpression append(IExpression expression) {
        expression.buildInit(this.order++, null, this); // TODO Context
        if(buildRegion != null && !buildRegion.match(expression)){
            this.buildRegion = null;
        }
        this.root = this.root.assembleTree(expression);
        return this.root;
    }

    @Override
    public void setBuildRegion(IBuildRegion region) {
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
    public void clearCommand() {
        this.commandStack.clear();
    }

    @Override
    public ITraveller<ICommand> commandTraveller() {
        return this.commandStack.iterator();
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
