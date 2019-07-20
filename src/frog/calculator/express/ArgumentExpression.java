package frog.calculator.express;

public class ArgumentExpression extends AbstractExpression {

    private IExpression valueExpression;

    public ArgumentExpression(String symbol) {
        super(symbol, null);
    }

    @Override
    public boolean createBranch(IExpression childExpression) {
        if(valueExpression == null){
            valueExpression = childExpression;
            return true;
        }else{
            IExpression root = valueExpression.assembleTree(childExpression);
            if(root == null){
                return false;
            }else{
                valueExpression = root;
                return true;
            }
        }
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        if(this.createBranch(expression)){
            return this;
        }else{
            return null;
        }
    }

    @Override
    public IExpression interpret() {
        return valueExpression.interpret();
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public int buildFactor() {
        return 1;
    }

    @Override
    public void setExpressionContext(IExpressionContext context) {
        // do nothing.
    }
}
