package frog.calculator.expressext.container;

import frog.calculator.expressext.AExpression;
import frog.calculator.expressext.IExpression;
import frog.calculator.expressext.IExpressionContext;

public class ContainerExpression extends AExpression {

    private IExpression content;

    private IExpressionContext context;

    public ContainerExpression(String symbol) {
        super(symbol);
    }

    @Override
    public boolean createBranch(IExpression childExpression) {
        return false;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        return null;
    }

    @Override
    public IExpression interpret() {
        return content.interpret();
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
    public void setExpressionContext(IExpressionContext context) {
        this.context = context;
    }
}
