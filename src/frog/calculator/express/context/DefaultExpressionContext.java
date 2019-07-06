package frog.calculator.express.context;

public class DefaultExpressionContext implements IExpressContext {

    private int minPriority;

    private int maxPriority;

    public DefaultExpressionContext(int minPriority, int maxPriority) {
        this.minPriority = minPriority;
        this.maxPriority = maxPriority;
    }

    @Override
    public int getMaxPriority() {
        return this.maxPriority;
    }

    @Override
    public int getMinPriority() {
        return this.minPriority;
    }
}
