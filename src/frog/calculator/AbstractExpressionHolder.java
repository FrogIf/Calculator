package frog.calculator;

import frog.calculator.express.IExpression;
import frog.calculator.express.MarkExpression;
import frog.calculator.express.SurroundExpression;

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
        int total = runnableExpression.length + 7;

        IExpression[] builtInExpression = new IExpression[total];
        int i = 0;
        for(; i < runnableExpression.length; i++){
            builtInExpression[i] = runnableExpression[i];
        }

        builtInExpression[i++] = this.separator;
        builtInExpression[i++] = this.bracketClose;
        builtInExpression[i++] = this.bracketOpen;
        builtInExpression[i++] = this.listEnd;
        builtInExpression[i++] = this.listFun;

        return builtInExpression;
    }

    protected abstract IExpression[] getRunnableExpression();

    @Override
    public IExpression getSeparator() {
        return this.separator;
    }

    @Override
    public IExpression getFunArgStart() {
        return this.bracketOpen;
    }

    @Override
    public IExpression getFunArgEnd() {
        return this.bracketClose;
    }

    @Override
    public IExpression getDelegate() {
        return new MarkExpression("->");
    }

    @Override
    public IExpression getAssign() {
        return new MarkExpression("=");
    }

    @Override
    public IExpression getDeclareBegin() {
        return new MarkExpression("@");
    }
}
