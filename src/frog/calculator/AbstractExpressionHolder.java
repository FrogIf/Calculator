package frog.calculator;

import frog.calculator.express.IExpression;
import frog.calculator.express.container.ContainerExpression;
import frog.calculator.express.endpoint.MarkExpression;
import frog.calculator.express.separator.LeftSepatatorExpression;
import frog.calculator.express.separator.SeparatorExpression;
import frog.calculator.operator.AssignOperator;
import frog.calculator.operator.DeclareOperator;
import frog.calculator.operator.DelegateOperator;
import frog.calculator.operator.StructContainerOperator;

public abstract class AbstractExpressionHolder implements IExpressionHolder {

    // 声明符号
    private IExpression declareBegin = new LeftSepatatorExpression("@", 0, new DeclareOperator(), true);

    // 赋值符号
    private IExpression assign = new SeparatorExpression("=", 0, new AssignOperator(), true);

    // 右括号
    private IExpression bracketClose = new MarkExpression(")");

    // 左括号
    private IExpression bracketOpen = new ContainerExpression("(", new StructContainerOperator(), bracketClose.symbol());

    // 分割符
    private IExpression separator = new MarkExpression(",");

    // 委托符号
    private IExpression delegate = new SeparatorExpression("->", 0, new DelegateOperator());

    @Override
    public IExpression getAssign() {
        return assign;
    }

    @Override
    public IExpression getDeclareBegin() {
        return this.declareBegin;
    }

    @Override
    public IExpression[] getBuiltInExpression() {
        IExpression[] runnableExpression = this.getRunnableExpression();
        int total = runnableExpression.length + 5;

        IExpression[] builtInExpression = new IExpression[total];
        int i = 0;
        for(; i < runnableExpression.length; i++){
            builtInExpression[i] = runnableExpression[i];
        }

        builtInExpression[i++] = this.assign;
        builtInExpression[i++] = this.separator;
        builtInExpression[i++] = this.bracketClose;
        builtInExpression[i++] = this.bracketOpen;
        builtInExpression[i] = this.delegate;

        return builtInExpression;
    }

    protected abstract IExpression[] getRunnableExpression();


    @Override
    public IExpression getSeparator() {
        return this.separator;
    }

    @Override
    public IExpression getContainerOpen() {
        return this.bracketOpen;
    }

    @Override
    public IExpression getContainerClose() {
        return this.bracketClose;
    }

    @Override
    public IExpression getDelegate() {
        return delegate;
    }
}
