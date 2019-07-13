package frog.calculator.express;

public class DefaultExpressionContext implements IExpressionContext {

    private int maxBuildFactor;

    private int minBuildFactor;

    @Override
    public int getCurrentMaxBuildFactor() {
        return maxBuildFactor;
    }

    @Override
    public int getCurrentMinBuildFactor() {
        return minBuildFactor;
    }

    @Override
    public void monitor(IExpression expression) {
        int bf = expression.buildFactor();
        if(maxBuildFactor < bf){
            maxBuildFactor = bf;
        }
        if(minBuildFactor > bf){
            minBuildFactor = bf;
        }
    }
}
