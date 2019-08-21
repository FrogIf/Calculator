package frog.calculator;

import frog.calculator.dimpl.opr.two.AddOperator;
import frog.calculator.dimpl.opr.two.SubOperator;
import frog.calculator.express.IExpression;
import frog.calculator.express.container.ContainerExpression;
import frog.calculator.express.container.FunctionExpression;
import frog.calculator.express.container.TupleExpression;
import frog.calculator.express.endpoint.MarkExpression;
import frog.calculator.express.separator.LeftSeparatorExpression;
import frog.calculator.express.separator.SeparatorExpression;
import frog.calculator.operator.*;

public abstract class AbstractExpressionHolder implements IExpressionHolder {

    // 声明符号
    private IExpression declareBegin = new LeftSeparatorExpression("@", 0, new DeclareOperator());

    // 赋值符号
    private IExpression assign = new SeparatorExpression("=", 0, new AssignOperator(), true);

    // 委托符号
    private IExpression delegate = new SeparatorExpression("->", 0, new DelegateOperator());

    // 右括号
    private IExpression bracketClose = new MarkExpression(")");

    // 分割符
    private IExpression separator = new MarkExpression(",");

    // 左括号
    private IExpression bracketOpen = new TupleExpression("(", separator.symbol(), bracketClose.symbol());

    // 正
    private IExpression plus = new SeparatorExpression("-", 1, new SubOperator());

    // 负
    private IExpression minus = new SeparatorExpression("+", 1, new AddOperator());

    // list 结束
    private IExpression listEnd = new MarkExpression("]");

    // 转list函数
    private IExpression listFun = new FunctionExpression("[", null, listEnd.symbol(), separator.symbol());

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
        int total = runnableExpression.length + 7;

        IExpression[] builtInExpression = new IExpression[total];
        int i = 0;
        for(; i < runnableExpression.length; i++){
            builtInExpression[i] = runnableExpression[i];
        }

        builtInExpression[i++] = this.assign;
        builtInExpression[i++] = this.separator;
        builtInExpression[i++] = this.bracketClose;
        builtInExpression[i++] = this.bracketOpen;
        builtInExpression[i++] = this.listEnd;
        builtInExpression[i++] = this.listFun;
        builtInExpression[i] = this.delegate;

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
        return delegate;
    }

    @Override
    public IExpression getPlus() {
        return plus;
    }

    @Override
    public IExpression getMinus() {
        return this.minus;
    }
}
