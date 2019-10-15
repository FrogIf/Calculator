package frog.calculator.express;

import frog.calculator.space.InterleavedSpace;

public class TupleExpression extends AbstractExpression {

    private String separatorSymbol;

    private String closeSymbol;

    private IExpression suspendExpression;

    private Element currentElement;

    private InterleavedSpace<Element> elements = new InterleavedSpace<>();

    private boolean isClose;

    public TupleExpression(String openSymbol, String separatorSymbol, String closeSymbol) {
        super(openSymbol, null);
        if(closeSymbol == null || separatorSymbol == null){
            throw new IllegalArgumentException("symbol is not enough.");
        }
        this.separatorSymbol = separatorSymbol;
        this.closeSymbol = closeSymbol;
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
    public boolean isLeaf() {
        return false;
    }

    @Override
    public int buildFactor() {
        return 0;
    }

    @Override
    public void setExpressionContext(IExpressionContext context) {

    }

    @Override
    public boolean hasNextChild() {
        return false;
    }

    @Override
    public IExpression nextChild() {
        return null;
    }

    private static class Element{
        private IExpression expression;

        @Override
        public String toString() {
            return expression.symbol();
        }
    }
}
