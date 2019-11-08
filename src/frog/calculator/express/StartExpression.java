package frog.calculator.express;

import frog.calculator.math.BaseNumber;
import frog.calculator.space.AtomSpace;
import frog.calculator.space.ISpace;

/**
 * 表达式起点</br>
 * 多线程安全
 */
public class StartExpression extends AbstractExpression {

    private StartExpression() {
        super("", null);
    }

    private static IExpression INSTANCE = new StartExpression();

    public static IExpression getInstance(){
        return INSTANCE;
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
    public void buildInit(int order, IExpressionContext context) {
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
