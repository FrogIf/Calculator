package frog.calculator.express;

import frog.calculator.util.LinkedList;

public class DefaultExpressionContext implements IExpressionContext {

    // 当前构造因子最大值
    private int maxBuildFactor = 1;

    // 当前构造因子最小值
    private int minBuildFactor = 0;

    private LinkedList<IExpression> varibleList = new LinkedList<>();

    @Override
    public int getCurrentMaxBuildFactor() {
        return maxBuildFactor;
    }

    @Override
    public int getCurrentMinBuildFactor() {
        return minBuildFactor;
    }

    @Override
    public IExpression[] getVariables() {
        // TODO 改为返回值是LinkedList
        IExpression[] exps = new IExpression[varibleList.size()];
        LinkedList<IExpression>.Iterator iterator = varibleList.getIterator();
        int i = 0;
        while(iterator.hasNext()){
            IExpression next = iterator.next();
            exps[i] = next;
            i++;
        }
        return exps;
    }

    @Override
    public void addVariables(IExpression expression) {
        varibleList.add(expression);
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

    @Override
    public IExpressionContext newInstance() {
        return new DefaultExpressionContext();
    }
}
