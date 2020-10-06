package frog.calculator.build;

import frog.calculator.build.resolve.IResolveResult;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.exception.BuildException;
import frog.calculator.exception.CalculatorError;
import frog.calculator.exception.ExpressionFormatException;
import frog.calculator.execute.space.AtomSpace;
import frog.calculator.execute.space.ISpace;
import frog.calculator.express.GhostExpression;
import frog.calculator.express.IExpression;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.util.Reference;
import frog.calculator.util.StringUtils;

public class DefaultExpressionTreeBuilder implements IExpressionTreeBuilder {

    // 表达式树初始节点, 当有其它表达式节点参与构建时, 会自动使用该节点替换初始节点
    private static final IExpression INIT_ROOT = new StartExpression();

    // 计算器管理器
    private final IBuildManager manager;

    public DefaultExpressionTreeBuilder(IBuildManager manager) {
        this.manager = manager;
    }

    @Override
    public IExpression build(char[] exp, ICalculatorSession session) throws BuildException {
        BuildContext context = new BuildContext(session, new CommandChain(manager.getCommandDetector()));
        Reference<IExpression> expRef = new Reference<>();
        try{
            IExpression root = INIT_ROOT;
            int order = 0;  // record symbol index
            context.setRoot(INIT_ROOT);

            for (int i = 0; i < exp.length; ) {
                // resolve
                int pos = this.resolve(exp, i, context, expRef);

                // build
                IExpression expression = expRef.getObj();
                expression.buildInit(order++, null, context); // TODO Context
                root = root.assembleTree(expression);
                if (root == null) {
                    throw new ExpressionFormatException(StringUtils.concat(exp), "expression format is not right at " + i);
                }

                context.setRoot(root);

                i = pos;
            }

            finish(context);

            return root;
        }catch (Exception e){
            this.failed(context);
            throw e;
        }
    }

    private int resolve(char[] chars, int startIndex, IBuildContext context, Reference<IExpression> ref) throws BuildException {
        // execute command before resolve
        CommandChain commandChain = context.commandChain();
        startIndex += commandChain.preBuild(chars, startIndex, context);

        /*
         * greedy strategy, the longest match
         */

        // 1. resolve from local register stack
        IVariableTableManager localVariableTableManager = context.getLocalVariableTableManager();
        IResolveResult result = localVariableTableManager.resolve(chars, startIndex);

        // 2. resolve from session, maybe there much better match
        ICalculatorSession session = context.getSession();
        IResolveResult sessionResult = session.resolve(chars, startIndex);
        if(!result.success() || (result.offset() < sessionResult.offset())){
            result = sessionResult;
        }

        // 3. resolve from system, maybe this is the best
        IResolveResult systemResult = this.manager.getResolver().resolve(chars, startIndex);
        if(!result.success() || result.offset() < systemResult.offset()){
            result = systemResult;
        }

        if(!result.success()){
            throw new ExpressionFormatException(String.valueOf(chars), "undefined symbol : " + chars[startIndex] + " at " + startIndex);
        }

        // execute command after resolve
        commandChain.postBuild(context);

        // set result
        ref.setObj(result.getExpression());

        int offset = result.offset();
        if (offset < 1) {
            throw new CalculatorError("system error : length of '" + result.getExpression().symbol() + "' is " + offset + ".");
        }

        return startIndex + offset;
    }

    private void failed(IBuildContext context){

    }

    private void finish(IBuildContext context){

    }

    /**
     * sentinel expression for expression start
     * Thread Safety
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

        private final ISpace<BaseNumber> VAL = new AtomSpace<>(BaseNumber.ZERO);

        @Override
        public ISpace<BaseNumber> interpret() {
            return this.VAL;
        }

        @Override
        public IExpression newInstance() {
            return this;
        }
    }

}
