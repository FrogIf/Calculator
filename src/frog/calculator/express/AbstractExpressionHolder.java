package frog.calculator.express;

public abstract class AbstractExpressionHolder implements IExpressionHolder {

    // 右括号
    private IExpression bracketClose = new MarkExpression(")");

    // 分割符
    private IExpression separator = new MarkExpression(",");

    // 左括号
    private IExpression bracketOpen = new SurroundExpression("(", separator.symbol(), bracketClose.symbol());

    // list 结束
    private IExpression listEnd = new MarkExpression("]");

    // 转list函数
    private IExpression listFun = new SurroundExpression("[", separator.symbol(), listEnd.symbol());

    @Override
    public IExpression[] getBuiltInExpression() {
        IExpression[] runnableExpression = this.getRunnableExpression();
        IExpression[] structureExpression = this.getStructureExpression();
        int total = runnableExpression.length + structureExpression.length;

        IExpression[] builtInExpression = new IExpression[total];
        int i = 0;
        for(; i < runnableExpression.length; i++){
            builtInExpression[i] = runnableExpression[i];
        }

        for(int j = 0; j < structureExpression.length; j++){
            builtInExpression[i + j] = structureExpression[j];
        }

        return builtInExpression;
    }

    @Override
    public IExpression[] getStructureExpression() {
        return new IExpression[]{
                bracketOpen,    // 左括号
                bracketClose,   // 右括号
                separator,      // 逗号
                listFun,        // 集合左
                listEnd         // 集合右
        };
    }

    protected abstract IExpression[] getRunnableExpression();

}
